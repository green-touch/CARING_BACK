package com.caring.user_service.common.util;

import java.net.InetAddress;
import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;

public class WorkerUtil {

    public static Duration backoff(int attempt){
        int base = Math.min(60, (int) Math.pow(2, attempt - 1));// sec
        int jitter = ThreadLocalRandom.current().nextInt(0, (int) (base * 0.2) + 1);
        return Duration.ofSeconds(base + jitter);
    }
    public static String shortErr(Exception e){
        return (e.getClass().getSimpleName() + ": " + e.getMessage());
    }
    public static Executor taskExecutor(){
        return ForkJoinPool.commonPool();
    }
    public static String workerId(){
        return InetAddress.getLoopbackAddress().getHostName()+"-"+Thread.currentThread().getId();
    }
}
