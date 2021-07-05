package com.example.demo.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MQConfig {

    public static final String MIAOSHA_QUEUE = "miaosha.queue";
    public static final String QUEUE = "queue";
    public static final String TOPIC_QUEUE1 = "topic.key1";
    public static final String TOPIC_QUEUE2 = "topic.key2";
    public static final String HEADER_QUEUE = "headerQueue";
    public static final String TOPIC_EXCHANGE = "topicExchange";
    public static final String FANOUT_EXCHANGE = "fanoutExchange";
    public static final String HEADERS_EXCHANGE = "headersExchange";

    @Bean
    public Queue queue(){
        return new Queue(MIAOSHA_QUEUE, true);
    }

    /**
     * Topic模式 交换机Exchange
     */
    @Bean
    public Queue topicQueue1(){
        return new Queue(TOPIC_QUEUE1, true);
    }

    @Bean
    public Queue topicQueue2(){
        return new Queue(TOPIC_QUEUE2, true);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding topicBinding1(){
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topic.key1");
    }

    @Bean
    public Binding topicBinding2(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.#");
    }

    /**
     * Fanout模式 广播
     */
    @Bean
    public FanoutExchange fanoutExahnge(){
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding fanoutBingding1(){
        return BindingBuilder.bind(topicQueue1()).to(fanoutExahnge());
    }

    @Bean
    public Binding fanoutBingding2(){
        return BindingBuilder.bind(topicQueue2()).to(fanoutExahnge());
    }

    /**
     * Header模式
     */
    @Bean
    public Queue headerQueue1(){
        return new Queue(HEADER_QUEUE, true);
    }

    @Bean
    public HeadersExchange headersExchange(){
        return new HeadersExchange(HEADERS_EXCHANGE);
    }

    @Bean
    public Binding headerBinding(){
        Map<String, Object> map = new HashMap<>();
        map.put("header1", "value1");
        map.put("header2", "value2");
        return BindingBuilder.bind(headerQueue1()).to(headersExchange()).whereAll(map).match();
    }
}
