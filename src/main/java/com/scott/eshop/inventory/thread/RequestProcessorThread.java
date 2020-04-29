package com.scott.eshop.inventory.thread;

import com.scott.eshop.inventory.request.Request;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * 执行请求的工作线程
 * @ClassName WorkerThread
 * @Description
 * @Author 47980
 * @Date 2020/4/29 21:24
 * @Version V_1.0
 **/
public class RequestProcessorThread implements Callable<Boolean> {

    /**
     * 自己监控的内存队列
     */
    private ArrayBlockingQueue<Request> queue;

    public RequestProcessorThread(ArrayBlockingQueue<Request> queue) {
        this.queue = queue;
    }


    @Override
    public Boolean call() throws Exception {
        while(true) {
            break;
        }
        return true;
    }
}
