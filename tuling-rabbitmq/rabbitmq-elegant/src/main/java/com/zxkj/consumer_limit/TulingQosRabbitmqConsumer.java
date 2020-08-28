package com.zxkj.consumer_limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *实例含义：
 * channel.basicQos(0,1,false);
 * 通过这个方法限制消费端一次读取的数量，消费端一个一个的处理
 * 并且消费端处理完一个之后需要ack（签收） 或者nack(拒绝签收) 之后，blocker才会发送下一个
 * 消息给消费端
 */
public class TulingQosRabbitmqConsumer {

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

        String exchangeName = "tuling.qos.direct";
        String exchangeType = "direct";
        String queueName = "tuling.qos.queue";
        String routingKey = "tuling.qos.key";

        channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchangeName,routingKey);

        /**
         * 限流设置:  prefetchSize：每条消息大小的设置 0 表示不限制
         * prefetchCount:标识每次推送多少条消息 一般是一条
         * global:false标识channel级别的  true:标识消费的级别的
         */
        channel.basicQos(0,1,false);

        /**
         * 消费端限流 需要关闭消息自动签收
         */
        channel.basicConsume(queueName,false,new TulingQosConsumer(channel));

    }
}
