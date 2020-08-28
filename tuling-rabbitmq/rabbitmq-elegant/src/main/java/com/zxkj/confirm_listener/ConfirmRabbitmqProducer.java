package com.zxkj.confirm_listener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class ConfirmRabbitmqProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("112.35.144.201");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("smlz");
        connectionFactory.setPassword("smlz");


        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        /**关键代码*/
        channel.confirmSelect();


        String exchangeName = "tuling.confirm.topicexchange";
        String routingKey = "tuling.confirm.key";

        Map<String,Object> tulingInfo = new HashMap<>();
        tulingInfo.put("company","tuling");
        tulingInfo.put("location","长沙");


        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .correlationId(UUID.randomUUID().toString())
                .timestamp(new Date())
                .expiration("1")
                .headers(tulingInfo)
                .build();

        String msgContext = "你好 图灵....";


        /**关键代码*/
        channel.addConfirmListener(new TulingConfirmListener());

        channel.basicPublish(exchangeName,routingKey,basicProperties,msgContext.getBytes());


    }
}
