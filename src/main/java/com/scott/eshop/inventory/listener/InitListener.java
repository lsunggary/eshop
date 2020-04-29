package com.scott.eshop.inventory.listener;

import com.scott.eshop.inventory.thread.RequestProcessorThreadPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 系统初始化监听器
 * @ClassName InitListener
 * @Description
 * @Author 47980
 * @Date 2020/4/29 20:13
 * @Version V_1.0
 **/
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 初始化工作线程池单例和工作队列
        RequestProcessorThreadPool.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
