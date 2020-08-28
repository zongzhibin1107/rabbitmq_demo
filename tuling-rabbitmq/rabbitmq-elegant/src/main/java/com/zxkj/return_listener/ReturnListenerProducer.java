package com.zxkj.return_listener;

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

/**
 * 此示例证明：
 * 当消息生产者生产了消息到blocker，但是blocker 无法发送消息
 * 这个时候会调用return方法
 *
 * 注意发送消息时，mandatory必须为true
 */
public class ReturnListenerProducer {

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


        String exchangeName = "tuling.retrun.direct";


        String okRoutingKey = "tuling.retrun.key.ok";
        String errorRoutingKey = "tuling.retrun.key.error";

        /**关键代码*/
        channel.addReturnListener(new TulingRetrunListener());


        Map<String,Object> tulingInfo = new HashMap<>();
        tulingInfo.put("company","tuling");
        tulingInfo.put("location","长沙");

        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .correlationId(UUID.randomUUID().toString())
                .timestamp(new Date())
                .headers(tulingInfo)
                .build();

        String msgContext = "你好 图灵...."+System.currentTimeMillis();


        /**
         * 发送消息
         * mandatory:该属性设置为false,那么不可达消息就会被mq broker给删除掉
         *          :true,那么mq会调用我们得retrunListener 来告诉我们业务系统 说该消息
         *          不能成功发送.
         *
         *
         */
        //channel.basicPublish(exchangeName,okRoutingKey,true,basicProperties,msgContext.getBytes());
        //channel.basicPublish(exchangeName,errorRoutingKey,true,basicProperties,msgContext.getBytes());
        channel.basicPublish(exchangeName,errorRoutingKey,false,basicProperties,msgContext.getBytes());

    }
}
