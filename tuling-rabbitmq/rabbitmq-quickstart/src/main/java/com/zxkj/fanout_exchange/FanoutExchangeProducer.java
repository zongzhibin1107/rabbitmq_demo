package com.zxkj.fanout_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FanoutExchangeProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("112.35.144.201");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("smlz");
        connectionFactory.setPassword("smlz");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();


        String exchangeName = "tuling.fanoutexchange";
        //定义routingKey
        String routingKey = "";
        String routingKey1 = "333";
        String routingKey2 = "4444";


        channel.basicPublish(exchangeName,routingKey,null,"我是第一条消息".getBytes());
        channel.basicPublish(exchangeName,routingKey1,null,"我是第二条消息".getBytes());
        channel.basicPublish(exchangeName,routingKey2,null,"我是第三条消息".getBytes());

        channel.close();
        connection.close();

    }
}
