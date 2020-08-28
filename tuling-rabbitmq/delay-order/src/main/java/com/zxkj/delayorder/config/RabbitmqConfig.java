package com.zxkj.delayorder.config;

import com.zxkj.delayorder.constants.MqConst;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitmqConfig {

    /**订单服务通知商品服务的消息 start*/
    @Bean
    public DirectExchange orderToProductExchange() {
        DirectExchange directExchange = new DirectExchange(MqConst.ORDER_TO_PRODUCT_EXCHANGE_NAME,true,false);
        return directExchange;
    }

    @Bean
    public Queue orderToProductQueue() {
        Queue queue = new Queue(MqConst.ORDER_TO_PRODUCT_QUEUE_NAME,true,false,false);
        return queue;
    }

    @Bean
    public Binding orderToProductBinding() {
        return BindingBuilder.bind(orderToProductQueue()).to(orderToProductExchange()).with(MqConst.ORDER_TO_PRODUCT_ROUTING_KEY);
    }
    /**订单服务通知商品服务的消息 end*/



    /**订单服务端延时检查任务 start*/
    @Bean
    public DirectExchange delayCheckExchange(){
        DirectExchange delayCheckExchange = new DirectExchange(MqConst.ORDER_DELAY_CHECK_EXCHANGE_NAME,true,false);
        return delayCheckExchange;
    }

    @Bean
    public Queue delayCheckQueue() {
        Queue queue = new Queue(MqConst.ORDER_DELAY_CHECK_QUEUE_NAME,true,false,false);
        return queue;
    }

    @Bean
    public Binding delayCheckBinding() {
        return BindingBuilder.bind(delayCheckQueue()).to(delayCheckExchange()).with(MqConst. ORDER_DELAY_CHECK_ROUTING_KEY);
    }
    /**订单服务端延时检查任务 end*/


    /** 30秒钟过期的消息配置 start*/
    @Bean
    public DirectExchange ttlExchange(){
        DirectExchange delayCheckExchange = new DirectExchange(MqConst.ORDER_TO_PRODUCT_TTL_EXCHANGE_NAME,true,false);
        return delayCheckExchange;
    }

    @Bean
    public Queue ttlQueue() {
        Map<String,Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange",MqConst.ORDER_DELAY_CHECK_EXCHANGE_NAME);
        args.put("x-dead-letter-routing-key",MqConst.ORDER_DELAY_CHECK_ROUTING_KEY);
        args.put("x-message-ttl",30000);
        Queue queue = new Queue(MqConst.ORDER_TO_PRODUCT_TTL_QUEUE_NAME,true,false,false,args);
        return queue;
    }

    @Bean
    public Binding ttlBinding() {
        return BindingBuilder.bind(ttlQueue()).to(ttlExchange()).with(MqConst. ORDER_TO_PRODUCT_TTL_ROUTING_KEY);
    }
    /** 30秒钟过期的消息配置 end*/



    /** 商品服务业务处理完成消息配置 start*/
    @Bean
    public Queue productCallBackQueue() {
        Queue queue = new Queue(MqConst.PRODUCT_TO_CALLBACK_QUEUE_NAME,true,false,false);
        return queue;
    }
    @Bean
    public Binding productCallBackBinding() {
        return BindingBuilder.bind(productCallBackQueue()).to(orderToProductExchange()).with(MqConst. PRODUCT_TO_CALLBACK_ROUTING_KEY);
    }
    /** 商品服务业务处理完成消息配置 end*/

}
