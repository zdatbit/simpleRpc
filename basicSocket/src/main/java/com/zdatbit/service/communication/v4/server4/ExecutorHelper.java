package com.zdatbit.service.communication.v4.server4;

import java.util.concurrent.*;

/**
 * 全局线程池
 */
public class ExecutorHelper {

    private static int cores = Runtime.getRuntime().availableProcessors();

    public static ExecutorService executorService = new ThreadPoolExecutor(cores,cores*2,10,
            TimeUnit.SECONDS,new ArrayBlockingQueue<>(1000));

}
