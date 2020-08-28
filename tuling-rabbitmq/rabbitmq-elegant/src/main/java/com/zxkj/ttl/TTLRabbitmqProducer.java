package com.zxkj.ttl;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 设置队列中消息的过期时间  过期之后 消息转到死信队列中
 */
public class TTLRabbitmqProducer {

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




        String exchangeName = "tuling.ttl.direct";
        String routingKey = "tuling.ttl.key";
        String queueName = "tuling.ttl.queue";

        String dlxExhcangeName = "tuling.dlx.exchange";
        channel.exchangeDeclare(exchangeName,"direct",true,false,null);
        Map<String,Object> queueArgs = new HashMap<>();
        //设置队列中的消息10s没有被消费就会过期
        queueArgs.put("x-message-ttl",10000);
        //队列的长度
        queueArgs.put("x-max-length",4);
        queueArgs.put("x-dead-letter-exchange",dlxExhcangeName);
        queueArgs.put("x-max-length",4);
        channel.queueDeclare(queueName,true,false,false,queueArgs);

        channel.queueBind(queueName,exchangeName,routingKey);

        String msgBody = "你好tuling";
        for(int i=0;i<10;i++) {
            channel.basicPublish(exchangeName,routingKey,null,(msgBody+i).getBytes());
        }


    }
}
