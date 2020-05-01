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

    /**
     * 是否强制刷新缓存
     */
    private boolean forceRefresh;

    public ProductInventoryCacheRefreshRequest(Integer productId, ProductInventoryService productInventoryService, boolean forceRefresh) {
        this.productId = productId;
        this.productInventoryService = productInventoryService;
        this.forceRefresh = forceRefresh;
    }

    @Override
    public void process() {
        // 从数据库中查询最新的商品库存数量
        ProductInventory productInventory = productInventoryService.findProductInventory(productId);
        System.out.println("===========日志===========：已查询到商品最新的库存数量: 商品id="+productId+", 商品库存数量="+productInventory.getInventory());
        // 将最新的商品数量更新到redis缓存中去
        productInventoryService.setProductInventoryCache(productInventory);
    }

    @Override
    public Integer getProductId() {
        return productId;
    }

    public boolean isForceRefresh() {
        return forceRefresh;
    }
}
