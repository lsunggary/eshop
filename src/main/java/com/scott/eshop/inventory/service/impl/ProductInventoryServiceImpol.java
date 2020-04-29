package com.scott.eshop.inventory.service.impl;

import com.scott.eshop.inventory.dao.RedisDAO;
import com.scott.eshop.inventory.mapper.ProductInventoryMapper;
import com.scott.eshop.inventory.model.ProductInventory;
import com.scott.eshop.inventory.service.ProductInventoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 商品库存 service 实现类
 * @ClassName ProductInventoryServiceImpol
 * @Description
 * @Author 47980
 * @Date 2020/4/29 22:11
 * @Version V_1.0
 **/
@Service("productInventoryService")
public class ProductInventoryServiceImpol implements ProductInventoryService {

    @Resource
    private ProductInventoryMapper productInventoryMapper;

    @Resource
    private RedisDAO redisDAO;

    @Override
    public void updateProductInventory(ProductInventory productInventory) {
        productInventoryMapper.updateProductInventory(productInventory);
    }

    @Override
    public void removeProductInventoryCache(ProductInventory productInventory) {
        String key = "product:inventroy:" + productInventory.getProductId();
        redisDAO.delete(key);
    }

    @Override
    public ProductInventory findProductInventory(Integer productId) {
        return productInventoryMapper.findProductInventory(productId);
    }

    @Override
    public void setProductInventoryCache(ProductInventory productInventory) {
        String key = "product:inventory:" + productInventory.getProductId();
        redisDAO.set(key, String.valueOf(productInventory.getInventory()));
    }
}
