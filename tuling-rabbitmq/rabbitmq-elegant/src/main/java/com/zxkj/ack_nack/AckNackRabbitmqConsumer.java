package com.zxkj.ack_nack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class AckNackRabbitmqConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("112.35.144.201");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("smlz");
        connectionFactory.setPassword("smlz");


        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();


        String exchangeName = "tuling.ack.direct";
        String exchangeType = "direct";
        String queueName = "tuling.ack.queue";
        String routingKey = "tuling.ack.key";

        channel.exchangeDeclare(exchangeName,exchangeType,true,true,null);
        channel.queueDeclare(queueName,true,false,true,null);
        channel.queueBind(queueName,exchangeName,routingKey);

        channel.basicConsume(queueName,false,new TulingAckConsumer(channel));
    }
}
