package com.scott.eshop.inventory.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.scott.eshop.inventory.dao.RedisDAO;
import com.scott.eshop.inventory.mapper.UserMapper;
import com.scott.eshop.inventory.model.User;
import com.scott.eshop.inventory.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户 Service 实现类
 * @ClassName UserServiceImpl
 * @Description
 * @Author 47980
 * @Date 2020/4/28 21:00
 * @Version V_1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisDAO redisDAO;

    @Override
    public User findUserInfo() {
        return userMapper.findUserInfo();
    }

    @Override
    public User getCacheUesrInfo() {
        redisDAO.set("cached_user", "{\"name\": \"lisi\", \"age\":28}");

        String userJSON = redisDAO.get("cached_user");
        JSONObject userJSONObject = JSONObject.parseObject(userJSON);

        User user = new User();
        user.setName(userJSONObject.getString("name"));
        user.setAge(userJSONObject.getInteger("age"));

        return user;
    }
}
