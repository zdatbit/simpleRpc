package com.zdatbit.common.utils;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class CommonPool {
    public static ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
}
