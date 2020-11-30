package com.m7.kafka;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ProduceThreadPool
 * @Description TODO
 * @Author huangxiao
 * @Deta 2020/11/12 4:29 下午
 * @Version 1.0
 */
public class ProduceThreadPool {
    public ThreadPoolExecutor threadPoolExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(12, 20, 60L,
                TimeUnit.SECONDS, new LinkedBlockingQueue(2000),new ThreadPoolExecutor.CallerRunsPolicy());
        return threadPoolExecutor;
    }
}
