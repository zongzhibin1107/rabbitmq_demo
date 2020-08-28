package com.zxkj.consumer_limit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class TulingQosConsumer extends DefaultConsumer {

    private Channel channel;

    public TulingQosConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body)
            throws IOException
    {
        System.out.println("consumerTag:"+consumerTag);
        System.out.println("envelope:"+envelope);
        System.out.println("properties:"+properties);
        System.out.println("body:"+new String(body));

        /**
         * multiple:false标识不批量签收
         */
        channel.basicNack(envelope.getDeliveryTag(),false,false);
        //channel.basicAck(envelope.getDeliveryTag(),false);
    }
}
