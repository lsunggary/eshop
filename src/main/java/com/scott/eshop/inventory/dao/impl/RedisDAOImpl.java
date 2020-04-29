package com.scott.eshop.inventory.dao.impl;

import com.scott.eshop.inventory.dao.RedisDAO;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

/**
 * @ClassName RedisDAOImpl
 * @Description
 * @Author 47980
 * @Date 2020/4/28 21:47
 * @Version V_1.0
 **/
@Component
public class RedisDAOImpl implements RedisDAO {

    @Resource
    private JedisCluster jedisCluster;

    @Override
    public void set(String key, String value) {
        jedisCluster.set(key, value);
    }

    @Override
    public String get(String key) {
        return jedisCluster.get(key);
    }

    @Override
    public void delete(String key) {
        jedisCluster.del(key);
    }
}
