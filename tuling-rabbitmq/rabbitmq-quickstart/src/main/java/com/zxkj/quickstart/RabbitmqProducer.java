package com.zxkj.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitmqProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("112.35.144.201");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("smlz");
        connectionFactory.setPassword("smlz");


        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        for (int i = 0; i < 5; i++) {
            String message ="hello -----" + i;
            channel.basicPublish("","tuling-queue-01",null,message.getBytes());
        }

        channel.close();
        connection.close();


    }
}
