package com.tengyue360.pool.factory;


import com.tengyue360.pool.ThreadProvider;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xuliang
 * @date 2017/10/20 10:58.
 **/
public class CustomThreadFactory  implements ThreadFactory {
    private AtomicInteger count = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        String threadName = ThreadProvider.class.getSimpleName() + count.addAndGet(1);
        t.setName(threadName);
        return t;
    }
}
