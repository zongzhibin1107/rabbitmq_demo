package com.zxkj.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class DlxConsumer1 extends DefaultConsumer {
    private Channel channel;


    public DlxConsumer1(Channel channel) {
        super(channel);
        this.channel = channel;
    }


    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)throws IOException
    {
        System.out.println("接受到消息:"+new String(body));
        //消费端拒绝签收，并且不支持重回队列，那么该条消息就是一条死信消息
        channel.basicNack(envelope.getDeliveryTag(),false,false);

        //channel.basicAck(envelope.getDeliveryTag(),false);
    }
}
