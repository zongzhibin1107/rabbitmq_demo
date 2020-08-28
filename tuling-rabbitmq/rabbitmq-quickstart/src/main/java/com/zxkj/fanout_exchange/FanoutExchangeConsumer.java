package com.zxkj.fanout_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FanoutExchangeConsumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("112.35.144.201");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("smlz");
        connectionFactory.setPassword("smlz");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();


        String exchangeName = "tuling.fanoutexchange";
        String exchangeType = "fanout";
        String quequName = "tuling.fanout.queue";
        String bingdingStr = "";

        channel.exchangeDeclare(exchangeName,exchangeType,true,true,null);
        channel.queueDeclare(quequName,true,false,true,null);
        channel.queueBind(quequName,exchangeName,bingdingStr);

        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume(quequName,true,queueingConsumer);
        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            System.out.println("接受到消息:"+new String(delivery.getBody()));
        }

    }

}
