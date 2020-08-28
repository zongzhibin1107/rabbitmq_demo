package com.zxkj.custom_consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by smlz on 2019/9/30.
 */
public class TulingRabbtimqConsumer  {

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

        //声明交换机
        String exchangeName = "tuling.customconsumer.direct";
        String exchangeType = "direct";
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);

        //声明队列
        String queueName = "tuling.customconsumer.queue";
        channel.queueDeclare(queueName,true,false,false,null);

        //交换机绑定队列
        String routingKey = "tuling.customconsumer.key";
        channel.queueBind(queueName,exchangeName,routingKey);

        channel.basicConsume(queueName,new TulingConsumer(channel));
    }
}
