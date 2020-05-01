package com.scott.eshop.inventory.thread;

import com.scott.eshop.inventory.request.ProductInventoryCacheRefreshRequest;
import com.scott.eshop.inventory.request.ProductInventoryDBUpdateRequest;
import com.scott.eshop.inventory.request.Request;
import com.scott.eshop.inventory.request.RequestQueue;

import java.util.Map;
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
        try {
            while(true) {

                // ArrayBlockingQueue
                // Blocking就是说明，如果队列满了，或者空了，那么都会在执行操作的时候，阻塞住。
                Request request = queue.take();
                boolean forceRefresh = request.isForceRefresh();

                // 检查是否强制刷新
                if (!forceRefresh) {
                    RequestQueue requestQueue = RequestQueue.getInstance();
                    // 先做读请求的去重
                    Map<Integer, Boolean> flagMap = requestQueue.getFlagMap();

                    if (request instanceof ProductInventoryDBUpdateRequest) {
                        // 如果是一个更新数据库的请求，那么九江那个productId对应的标识位置为true
                        flagMap.put(request.getProductId(), true);
                    } else if (request instanceof ProductInventoryCacheRefreshRequest) {
                        Boolean flag = flagMap.get(request.getProductId());
                        // 如果flag为null
                        if (flag == null) {
                            flagMap.put(request.getProductId(), false);
                        }

                        // 如果是缓存刷新的请求，那么就判断，如果表示不为空，而且是true，就说明之前有一个商品的更新请求。
                        if (flag != null && flag) {
                            flagMap.put(request.getProductId(), false);
                        }

                        // 如果是缓存刷新的请求，而且发现标识不为空，但是标识是false，说明前面已经数据库更新请求+一个缓存刷新请求了。
                        if (flag != null && !flag) {
                            // 对于这种读请求，直接就过滤掉。
                            continue;
                        }
                    }
                }

                System.out.println("===========日志===========：工作线程处理请求，商品id："+request.getProductId());
                // 执行process
                request.process();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
