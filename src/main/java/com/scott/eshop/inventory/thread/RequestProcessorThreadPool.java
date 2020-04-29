package com.scott.eshop.inventory.thread;

import com.scott.eshop.inventory.request.Request;
import com.scott.eshop.inventory.request.RequestQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池： 单例
 * @ClassName ThreadPool
 * @Description
 * @Author 47980
 * @Date 2020/4/29 20:17
 * @Version V_1.0
 **/
public class RequestProcessorThreadPool {

    // 实际项目中都可以写到配置文件中
    /**
     * 线程池
     */
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    /**
     * 构造方法
     *
     * 初始化的事情
     */
    public RequestProcessorThreadPool() {
        // 根据请求队列单例获取到对象
        RequestQueue requestQueue = RequestQueue.getInstance();
        for (int i = 0; i < 10; i++) {
            // 初始化队列
            ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue<>(100);
            requestQueue.addQueue(queue);
            // 并将队列和线程相互绑定
            // 丢入线程池中
            threadPool.submit(new RequestProcessorThread(queue));
        }
    }

    /**
     * 单例方式很多
     * 这边采取绝对线程安全的一种方式
     * 静态内部类的方式，去初始化单例
     */
    private static class Singleten {

        private static RequestProcessorThreadPool instance;

        static {
            instance = new RequestProcessorThreadPool();
        }

        public static RequestProcessorThreadPool getInstance () {
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
    public static RequestProcessorThreadPool getInstance() {
        return Singleten.getInstance();
    }

    /**
     * 初始化便捷方法
     */
    public static void init() {
        Singleten.getInstance();
    }
}
