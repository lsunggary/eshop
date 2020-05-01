package com.scott.eshop.inventory.service.impl;

import com.scott.eshop.inventory.request.ProductInventoryCacheRefreshRequest;
import com.scott.eshop.inventory.request.ProductInventoryDBUpdateRequest;
import com.scott.eshop.inventory.request.Request;
import com.scott.eshop.inventory.request.RequestQueue;
import com.scott.eshop.inventory.service.RequestAsyncProcessService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 请求异步处理的service实现
 * @ClassName RequestAsyncProcessServiceImpl
 * @Description
 * @Author 47980
 * @Date 2020/5/1 12:33
 * @Version V_1.0
 **/
@Service("requestAsyncProcessService")
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {
    @Override
    public void process(Request request) {
        try {
            // 做请求的路由，根据每个请求的商品id，路由到对应的内存队列中去
            // ArrayBlockingQueue 多线程并发安全
            ArrayBlockingQueue<Request> queue = getRoutingQueue(request.getProductId());
            // 将请求放入对应的队列中
            queue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取路由到的内存队列
     * @param productId 商品id
     * @return 内存队列
     */
    private ArrayBlockingQueue<Request> getRoutingQueue(Integer productId) {
        RequestQueue requestQueue = RequestQueue.getInstance();

        // 先获取productId的hash值
        String key = String.valueOf(productId);
        int h;
        int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);

        // 对hash值取模，将hash值路由到指定的内存队列中
        // 比如内存队列大小8
        // 用内存队列的数量对hash值取模后，结果一定在0-7之间
        // 那么无论一个商品ID都是在内存队列中的。
        int index = (requestQueue.queueSize() - 1) & hash;

        System.out.println("===========日志===========: 路由内存队列，商品id="+ productId+" ，队列索引="+index);

        return requestQueue.getQueue(index);
    }


}
