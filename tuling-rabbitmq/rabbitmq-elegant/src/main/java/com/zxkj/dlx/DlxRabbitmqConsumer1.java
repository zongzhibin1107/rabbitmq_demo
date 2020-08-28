package com.zxkj.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 用于消费死信队列消息的消费端  和平常的消费端一样
 */
public class DlxRabbitmqConsumer1 {

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
        String dlxQueueName = "tuling.dlx.queue";

        channel.basicQos(0,1,false);

        channel.basicConsume(dlxQueueName,false,new DlxConsumer1(channel));

    }
}
