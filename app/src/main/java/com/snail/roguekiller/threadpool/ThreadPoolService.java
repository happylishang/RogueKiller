package com.snail.roguekiller.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author: hzlishang
 * Data: 16/7/28 下午2:10
 * Des:
 * version:
 */
public class ThreadPoolService {

    static ThreadPoolService sThreadPoolService;

    private static int sCorePoolSize = 5;

    private static int sMaximumPoolSize = 10;

    private static int sKeepAliveTime = 5000;

    private ThreadPoolExecutor mThreadPoolExecutor;

    public static ThreadPoolService getInstance() {
        return new ThreadPoolService();
    }

    private ThreadPoolService() {
        BlockingQueue<Runnable> mWorkQueue = new LinkedBlockingDeque<>();
        mThreadPoolExecutor = new ThreadPoolExecutor(sCorePoolSize, sMaximumPoolSize, sKeepAliveTime, TimeUnit.MILLISECONDS, mWorkQueue);
    }

    public void addTask(Runnable runnable) {
        mThreadPoolExecutor.execute(runnable);
    }
}
