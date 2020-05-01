package com.scott.eshop.inventory.service;

import com.scott.eshop.inventory.request.Request;

/**
 * 请求异步执行的service
 * @ClassName RequestAsyncProcessService
 * @Description interface
 * @Author 47980
 * @Date 2020/5/1 12:29
 * @Version V_1.0
 **/
public interface RequestAsyncProcessService {

    void process(Request request);
}
