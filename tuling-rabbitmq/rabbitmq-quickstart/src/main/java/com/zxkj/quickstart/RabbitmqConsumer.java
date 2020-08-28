package com.zxkj.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitmqConsumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("112.35.144.201");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("smlz");
        connectionFactory.setPassword("smlz");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();


        String queueName = "tuling-queue-01";

        channel.queueDeclare(queueName,true,false,true,null);

        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);


        channel.basicConsume(queueName,true,queueingConsumer);


        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String reserveMsg = new String(delivery.getBody());
            System.out.println("消费消息:"+reserveMsg);
        }
    }
}
