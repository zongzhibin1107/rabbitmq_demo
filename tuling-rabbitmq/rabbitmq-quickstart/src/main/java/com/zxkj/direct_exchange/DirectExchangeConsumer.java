package com.zxkj.direct_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DirectExchangeConsumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("112.35.144.201");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("smlz");
        connectionFactory.setPassword("smlz");


        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "tuling.directchange";
        String exchangeType = "direct";
        String queueName = "tuling.directqueue";
        String routingKey = "tuling.directchange.key";


        channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);

        channel.queueDeclare(queueName,true,false,false,null);

        channel.queueBind(queueName,exchangeName,routingKey);


        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        channel.basicConsume(queueName,true,queueingConsumer);

        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String reciverMessage = new String(delivery.getBody());
            System.out.println("消费消息:-----"+reciverMessage);
        }

    }

}


