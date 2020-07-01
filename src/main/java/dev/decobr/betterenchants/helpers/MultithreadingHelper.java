package dev.decobr.betterenchants.helpers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MultithreadingHelper {
    private static final ScheduledExecutorService SCHEDULED_POOL = Executors.newScheduledThreadPool(3);
    public static ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    public static void schedule(Runnable r, long delay, TimeUnit timeUnit) {
        SCHEDULED_POOL.schedule(r, delay, timeUnit);
    }

    public static void run(Runnable r) {
        EXECUTOR_SERVICE.execute(r);
    }
}
