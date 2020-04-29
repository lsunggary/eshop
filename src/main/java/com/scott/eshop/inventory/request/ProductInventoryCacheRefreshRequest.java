package com.scott.eshop.inventory.request;

import com.scott.eshop.inventory.model.ProductInventory;
import com.scott.eshop.inventory.service.ProductInventoryService;

/**
 * 重新加载商品库存的缓存
 * @ClassName ProductInventoryCacheReloadRequest
 * @Description
 * @Author 47980
 * @Date 2020/4/29 22:20
 * @Version V_1.0
 **/
public class ProductInventoryCacheRefreshRequest implements Request {

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 商品库存service
     */
    private ProductInventoryService productInventoryService;

    public ProductInventoryCacheRefreshRequest(Integer productId, ProductInventoryService productInventoryService) {
        this.productId = productId;
        this.productInventoryService = productInventoryService;
    }

    @Override
    public void process() {
        // 从数据库中查询最新的商品库存数量
        ProductInventory productInventory = productInventoryService.findProductInventory(productId);
        // 将最新的商品数量更新到redis缓存中去
        productInventoryService.setProductInventoryCache(productInventory);
    }
}
