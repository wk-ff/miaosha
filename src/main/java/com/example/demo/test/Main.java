package com.example.demo.test;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.concurrent.*;

public class Main {
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 6;
    private static final int QUEUE_CAPACITY = 1;
    private static final long KEEP_ALIVE_TIME = 2;
    public static void main(String[] args) {

        //使用阿里巴巴推荐的创建线程池的方式
        //通过ThreadPoolExecutor构造函数自定义参数创建
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(
//                CORE_POOL_SIZE,
//                MAX_POOL_SIZE,
//                KEEP_ALIVE_TIME,
//                TimeUnit.SECONDS,
//                new ArrayBlockingQueue<Runnable>(QUEUE_CAPACITY),
//                new ThreadPoolExecutor.AbortPolicy());
//
//        for (int i = 0; i < 10; i++) {
//            //创建WorkerThread对象（WorkerThread类实现了Runnable 接口）
//            Runnable worker = new MyRunable("" + i);
//            //执行Runnable
//            executor.execute(worker);
//        }
//        //终止线程池
//        executor.shutdown();
//        while (!executor.isTerminated()) {
//        }
        String s = "abcds";
        System.out.println(Integer.MAX_VALUE);
    }
}
