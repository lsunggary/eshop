package com.scott.eshop.inventory.service;

import com.scott.eshop.inventory.model.User;

/**
 * 用户service接口
 * @ClassName UserService
 * @Description interface
 * @Author 47980
 * @Date 2020/4/28 20:59
 * @Version V_1.0
 **/
public interface UserService {

    /**
     * 查询用户信息
     * @return
     */
    User findUserInfo();

    /**
     * 查询缓存的用户信息
     * @return
     */
    User getCacheUesrInfo();
}
