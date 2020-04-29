package com.scott.eshop.inventory.model;

/**
 * 库存数量model
 * @ClassName ProductInventory
 * @Description
 * @Author 47980
 * @Date 2020/4/29 21:59
 * @Version V_1.0
 **/
public class ProductInventory {

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 库存数量
     */
    private Long inventory;

    public ProductInventory(Integer productId, Long inventory) {
        this.productId = productId;
        this.inventory = inventory;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Long getInventory() {
        return inventory;
    }

    public void setInventory(Long inventory) {
        this.inventory = inventory;
    }
}
