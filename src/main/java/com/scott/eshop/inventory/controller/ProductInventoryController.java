package com.scott.eshop.inventory.controller;

import com.scott.eshop.inventory.model.ProductInventory;
import com.scott.eshop.inventory.request.ProductInventoryCacheRefreshRequest;
import com.scott.eshop.inventory.request.ProductInventoryDBUpdateRequest;
import com.scott.eshop.inventory.request.Request;
import com.scott.eshop.inventory.service.ProductInventoryService;
import com.scott.eshop.inventory.service.RequestAsyncProcessService;
import com.scott.eshop.inventory.vo.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 商品库存Controller
 *
 * （1） 一个更新请求过来，此时会删除redis中的缓存，然后模拟卡顿5秒
 * （2） 在这卡顿的5秒内，我们会发送一个商品缓存的读请求，因为此时redis中没有缓存，就会请求将数据库中最新请求更新到缓存中
 * （3） 此时读请求会路由到同一个内存队列中，阻塞住，不会执行
 * （4） 等5秒后，写请求完成数据库的更新之后，读请求才会执行
 * （5） 读请求执行的时候，会将最新的库存从数据库中查询出来，然后更新到缓存中
 *
 * 如果不一致，那么redis和数据库的数量时不一致的。
 *
 * @ClassName ProductInventoryController
 * @Description
 * @Author 47980
 * @Date 2020/5/1 12:52
 * @Version V_1.0
 **/
@Controller
public class ProductInventoryController {

    @Resource
    private RequestAsyncProcessService requestAsyncProcessService;

    @Resource
    private ProductInventoryService productInventoryService;

    /**
     * 商品更新
     * @param productInventory
     * @return
     */
    @RequestMapping("/updateProductInventory")
    @ResponseBody
    public Response updateProductInventory(ProductInventory productInventory) {
        System.out.println("===========日志===========: 接受到更新商品库存的请求，商品id="+ productInventory.getProductId()+" ，商品库存="+productInventory.getInventory());

        Response response = null;
        try {
            Request request = new ProductInventoryDBUpdateRequest(productInventory, productInventoryService);
            requestAsyncProcessService.process(request);
            response = new Response(Response.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response(Response.FAILURE);
        }
        return response;
    }

    /**
     * 获取商品库存
     * @param productId
     * @return
     */
    @RequestMapping("/getProductInventory")
    @ResponseBody
    public ProductInventory getProductInventory(Integer productId) {
        System.out.println("===========日志===========：接收到商品信息的读请求: 商品id="+productId);

        ProductInventory productInventory = null;
        try {
            Request request = new ProductInventoryCacheRefreshRequest(productId, productInventoryService, false);
            requestAsyncProcessService.process(request);
            // 将请求扔给service异步去处理以后，需要while（true）一会，这里hang住
            // 去尝试等待前面有商品库存更新的操作，同时缓存刷新的操作，将最新的数据刷新到缓存里
            long startTime = System.currentTimeMillis();
            long waitTime = 0L;
            long endTime = 0L;

            while (true) {
                // 如果等待超过200ms就终止循环
                if (waitTime > 200) {
                    break;
                }
                // 尝试从redis中读取一次商品的缓存数据
                productInventory = productInventoryService.getProductInventoryCache(productId);
                // 如果读取到了结果，那么就回返
                if (productInventory != null) {
                    System.out.println("===========日志===========：在200ms内读取到了redis中的缓存，商品id="+productInventory.getProductId()+", 商品库存="+productInventory.getInventory());
                    return productInventory;
                }

                // 如果没有读取到结果，那么就等待一段时间
                else {
                    Thread.sleep(20);
                    endTime = System.currentTimeMillis();
                    waitTime = endTime - startTime;
                }
            }
            // 直接尝试从数据库中读取数据
            productInventory = productInventoryService.findProductInventory(productId);
            // 如果获取到数据，直接返回
            if (productInventory != null) {
                // 将缓存强制刷新一下
                request = new ProductInventoryCacheRefreshRequest(productId, productInventoryService, true);
                // 这个过程中，实际上时一个读操作的过程，并且并不在队列中串行处理。还是会有不一致的问题。
                requestAsyncProcessService.process(request);
                System.out.println("===========日志===========：超过200ms，读取数据库数据，并强制刷新缓存，商品id="+productInventory.getProductId()+", 商品库存="+productInventory.getInventory());
                // 代码会运行到这里，只有三种情况。
                // 1. 上一次的读请求，数据刷新了redis，但是被redis 的LRU算法给清理掉了。
                // 2. 可能在200ms内，也就是读请求在队列中一直积压。没有等待到执行。实际这种情况，就应该要么加机器，要么优化sql了。
                // 3. 数据库本身就没有。缓存穿透，穿透redis，请求达到mysql库。
                return productInventory;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new ProductInventory(productId, -1L);
    }
}
