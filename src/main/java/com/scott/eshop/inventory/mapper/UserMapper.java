package com.scott.eshop.inventory.mapper;

import com.scott.eshop.inventory.model.User;

/**
 * 测试用户的 Mapper 接口
 * @ClassName UserMapper
 * @Description
 * @Author 47980
 * @Date 2020/4/28 20:57
 * @Version V_1.0
 **/
public interface UserMapper {

    /**
     * 查询用户信息
     * @return
     */
    public User findUserInfo();
}
