package com.tengyue360.pool.handler;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author xuliang
 * @date 2017/10/20 10:59.
 **/
public class CustomHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        try {
            // put阻塞方法
            executor.getQueue().put(r);
        } catch (InterruptedException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
            //中断线程操作 重新跑批isInterrupted方法 并不意味着线程退出
            Thread.currentThread().interrupt();
        }
    }
}
