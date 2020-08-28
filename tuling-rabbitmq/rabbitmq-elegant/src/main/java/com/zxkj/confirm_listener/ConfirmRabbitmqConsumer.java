package com.zxkj.confirm_listener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConfirmRabbitmqConsumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("112.35.144.201");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("smlz");
        connectionFactory.setPassword("smlz");


        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();


        String exchangeName = "tuling.confirm.topicexchange";
        String exchangeType = "topic";
        String queueName = "tuling.confirm.queue";
        String routingKey = "tuling.confirm.#";

        channel.exchangeDeclare(exchangeName,exchangeType,true,true,null);
        channel.queueDeclare(queueName,true,false,true,null);
        channel.queueBind(queueName,exchangeName,routingKey);

        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        //消费消息
        channel.basicConsume(queueName,true,queueingConsumer);

        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            System.out.println("消费端在:"+System.currentTimeMillis()+"消费:"+new String(delivery.getBody()));
            System.out.println("correlationId:"+delivery.getProperties().getCorrelationId());
        }
    }
}
