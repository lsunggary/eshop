package com.scott.eshop.inventory.request;

import com.scott.eshop.inventory.thread.RequestProcessorThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 请求内存队列
 * @ClassName RequestQueue
 * @Description
 * @Author 47980
 * @Date 2020/4/29 21:29
 * @Version V_1.0
 **/
public class RequestQueue {

    /**
     * 内存队列
     */
    private List<ArrayBlockingQueue<Request>> queues = new ArrayList<>();

    /**
     * 单例方式很多
     * 这边采取绝对线程安全的一种方式
     * 静态内部类的方式，去初始化单例
     */
    private static class Singleten {

        private static RequestQueue instance;

        static {
            instance = new RequestQueue();
        }

        public static RequestQueue getInstance () {
            return instance;
        }
    }

    /**
     * JVM的机制去保障多线程并发安全
     *
     * 内部类的初始化，一定只会发生一次，不管多少个线程去并发初始化
     *
     * @return
     */
    public static RequestQueue getInstance() {
        return RequestQueue.Singleten.getInstance();
    }

    public void addQueue(ArrayBlockingQueue<Request> queue) {
        this.queues.add(queue);
    }

}
