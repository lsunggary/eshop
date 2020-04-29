package com.scott.eshop.inventory.service;

import com.scott.eshop.inventory.model.ProductInventory;

/**
 * 商品
 * @ClassName ProductInventoryService
 * @Description interface
 * @Author 47980
 * @Date 2020/4/29 22:03
 * @Version V_1.0
 **/
public interface ProductInventoryService {

    /**
     * 更新商品库存
     * @param productInventory 商品库存
     */
    void updateProductInventory(ProductInventory productInventory);

    /**
     * 删除redis中商品库存的缓存
     * @param productInventory 商品库存
     */
    void removeProductInventoryCache(ProductInventory productInventory);

    /**
     * 通过商品id查询商品信息
     * @param productId 商品id
     * @return 商品信息
     */
    ProductInventory findProductInventory(Integer productId);

    /**
     * 设置商品缓存
     * @param productInventory 商品库存
     */
    void setProductInventoryCache(ProductInventory productInventory);
}
