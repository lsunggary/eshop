package com.scott.eshop.inventory.mapper;

import com.scott.eshop.inventory.model.ProductInventory;

/**
 * 库存数量mapper
 * @ClassName ProductInventoryMapper
 * @Description interface
 * @Author 47980
 * @Date 2020/4/29 21:57
 * @Version V_1.0
 **/
public interface ProductInventoryMapper {

    /**
     * 更新库存数量
     * @param productInventory
     */
    void updateProductInventory(ProductInventory productInventory);

    /**
     * 更新商品Id查询商品库存信息
     * @param productId 商品id
     * @return 商品库存信息
     */
    ProductInventory findProductInventory(Integer productId);
}
