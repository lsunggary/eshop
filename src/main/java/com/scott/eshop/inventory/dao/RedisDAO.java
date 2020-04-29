package com.scott.eshop.inventory.dao;

/**
 * Redis dao类
 * @ClassName RedisDAO
 * @Description
 * @Author 47980
 * @Date 2020/4/28 21:45
 * @Version V_1.0
 **/
public interface RedisDAO {

    void set(String key, String value);

    String get(String key);

    void delete(String key);
}
