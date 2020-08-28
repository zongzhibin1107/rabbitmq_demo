package com.zxkj.ack_nack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class AckNackRabbitmqProducer {

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

        String routingKey = "tuling.ack.key";

        String msgBody = "你好tuling";


        for(int i=0;i<10;i++) {

            Map<String,Object> infoMap = new HashMap<>();
            infoMap.put("mark",i);

            AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
                    .deliveryMode(2)//消息持久化
                    .contentEncoding("UTF-8")
                    .correlationId(UUID.randomUUID().toString())
                    .headers(infoMap)
                    .build();
            channel.basicPublish(exchangeName,routingKey,basicProperties,(msgBody+i).getBytes());
        }


    }
}
