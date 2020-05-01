package com.scott.eshop.inventory.request;

import com.scott.eshop.inventory.dao.RedisDAO;
import com.scott.eshop.inventory.model.ProductInventory;
import com.scott.eshop.inventory.service.ProductInventoryService;

/**
 * 商品发生了交易，需要修改库存
 *
 * 此时就会发送请求过来，要求修改数据库，那么就可能时所谓的data update request， 数据更新请求
 *
 * cache aside pattern
 *
 * （1） 删除缓存
 * （2） 更新数据库
 *
 * @ClassName DataUpdateRequest
 * @Description
 * @Author 47980
 * @Date 2020/4/29 21:45
 * @Version V_1.0
 **/
public class ProductInventoryDBUpdateRequest implements Request{

    /**
     * 商品库存
     */
    private ProductInventory productInventory;

    /**
     * 商品库存service
     */
    private ProductInventoryService productInventoryService;

    public ProductInventoryDBUpdateRequest(ProductInventory productInventory, ProductInventoryService productInventoryService) {
        this.productInventory = productInventory;
        this.productInventoryService = productInventoryService;
    }

    @Override
    public void process() throws InterruptedException {
        System.out.println("===========日志===========：数据库更新请求开始执行，商品id："+productInventory.getProductId()+" ,商品库存："+productInventory.getInventory());
        // 删除redis中的缓存
        productInventoryService.removeProductInventoryCache(productInventory);
        System.out.println("===========日志===========：以删除redis库的缓存");
//        Thread.sleep(5000L);
        // 修改数据库中的库存
        productInventoryService.updateProductInventory(productInventory);
    }

    /**
     * 获取商品id
     * @return
     */
    @Override
    public Integer getProductId() {
        return productInventory.getProductId();
    }

    @Override
    public boolean isForceRefresh() {
        return false;
    }
}
