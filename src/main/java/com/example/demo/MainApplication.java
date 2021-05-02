package com.example.demo;

import com.example.demo.redis.RedisConfiguration;
import com.weicoder.common.C;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sun.applet.Main;

import java.util.HashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author wk-ff
 */
//@EnableAutoConfiguration
@SpringBootApplication
public class MainApplication{

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
