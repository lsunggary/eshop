package com.scott.eshop.inventory.controller;

import com.scott.eshop.inventory.model.User;
import com.scott.eshop.inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户 controller 控制器
 * @ClassName UserController
 * @Description
 * @Author 47980
 * @Date 2020/4/28 21:01
 * @Version V_1.0
 **/
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getUserInfo")
    @ResponseBody
    public User getUserInfo() {
        return userService.findUserInfo();
    }

    @RequestMapping("/getCachedUserInfo")
    @ResponseBody
    public User getCachedUserInfo() {
        return userService.getCacheUesrInfo();
    }
}
