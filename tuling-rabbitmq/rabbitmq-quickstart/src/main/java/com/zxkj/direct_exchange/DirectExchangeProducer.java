package com.zxkj.direct_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DirectExchangeProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("112.35.144.201");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("smlz");
        connectionFactory.setPassword("smlz");


        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        //定义交换机名称
        String exchangeName = "tuling.directchange";
        //定义routingKey
        String routingKey = "tuling.directchange.key";
        String messageBody = "hello tuling ";
        channel.basicPublish(exchangeName,routingKey,null,messageBody.getBytes());

        channel.close();
        connection.close();
    }

}
