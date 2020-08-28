package com.zxkj.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 *死信队列的演示   可用于订单未支付取消订单的功能
 */
public class DlxRabbitmqConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("112.35.144.201");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("smlz");
        connectionFactory.setPassword("smlz");
        connectionFactory.setConnectionTimeout(100000);

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        //声明正常的队列
        String nomalExchangeName = "tuling.nomaldlx.exchange";
        String exchangeType = "topic";
        String nomalqueueName = "tuling.nomaldlx.queue";
        String routingKey = "tuling.dlx.#";

        channel.exchangeDeclare(nomalExchangeName,exchangeType,true,false,null);

        Map<String,Object> queueArgs = new HashMap<>();

        String dlxExhcangeName = "tuling.dlx.exchange";
        String dlxQueueName = "tuling.dlx.queue";
        //正常队列上绑定死信队列
        queueArgs.put("x-dead-letter-exchange",dlxExhcangeName);
        queueArgs.put("x-max-length",4);

        channel.queueDeclare(nomalqueueName,true,false,false,queueArgs);
        channel.queueBind(nomalqueueName,nomalExchangeName,routingKey);

        //声明死信队列
        channel.exchangeDeclare(dlxExhcangeName,exchangeType,true,false,null);
        channel.queueDeclare(dlxQueueName,true,false,false,null);
        channel.queueBind(dlxQueueName,dlxExhcangeName,"#");


        channel.basicConsume(nomalqueueName,false,new DlxConsumer(channel));

    }
}
