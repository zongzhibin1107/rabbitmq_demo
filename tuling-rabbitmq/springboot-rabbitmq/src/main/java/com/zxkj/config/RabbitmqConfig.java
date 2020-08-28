package com.zxkj.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitmqConfig {

    @Bean
    public DirectExchange orderToProductExchange(){
        DirectExchange directExchange =
                new DirectExchange("springboot-direct-exchange", true, false);
        return directExchange;
    }

    @Bean
    public Queue orderToProductQueue(){
        Queue queue =
                new Queue("springboot-direct-queue",true,false,false);
        return queue;
    }


    @Bean
    public Binding orderToProductBinding() {
        return BindingBuilder.bind(orderToProductQueue()).to(orderToProductExchange())
                .with("springboot-direct-key");
    }



    @Bean
    public TopicExchange orderToProductTopicExchange(){
        TopicExchange topicExchange =
                new TopicExchange("springboot.topic.exchange", true, false);
        return topicExchange;
    }

    @Bean
    public Queue orderToProductTopicQueue(){
        Queue queue =
                new Queue("springboot.topic.queue",true,false,false);
        return queue;
    }

    @Bean
    public Queue orderToProductTopicQueue1(){
        Queue queue =
                new Queue("springboot.topic.queue1",true,false,false);
        return queue;
    }


    @Bean
    public Binding orderToProductTopicBinding() {
        return BindingBuilder.bind(orderToProductTopicQueue()).to(orderToProductTopicExchange())
                .with("springboot.key.2");
    }
    @Bean
    public Binding orderToProductTopicBinding1() {
        return BindingBuilder.bind(orderToProductTopicQueue1()).to(orderToProductTopicExchange())
                .with("springboot.key.1");
    }


    public final static String DELAY_EXCHANGE_NAME = "spring_delay_exchange";
    public final static String DELAY_QUEUE_NAME = "spring.delay.queue";
    public final static String DELAY_ROUTING_KEY = "spring.rabbit";

    @Bean
    public CustomExchange delayExchange(){
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAY_EXCHANGE_NAME, "x-delayed-message",true, false,args);
    }

    @Bean
    public Queue delayQueue(){
        return  new Queue(DELAY_QUEUE_NAME, true);
    }

    @Bean
    public Binding delayBinding(){
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(DELAY_ROUTING_KEY).noargs();
    }
}
