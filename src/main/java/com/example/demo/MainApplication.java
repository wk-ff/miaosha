package com.example.demo;

import com.example.demo.dao.QueryDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wk-ff
 */
//@EnableAutoConfiguration
@SpringBootApplication
public class MainApplication {

    @Autowired
    QueryDemo queryDemo;

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
