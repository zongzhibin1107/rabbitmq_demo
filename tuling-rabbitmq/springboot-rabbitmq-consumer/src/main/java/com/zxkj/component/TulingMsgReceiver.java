package com.zxkj.component;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TulingMsgReceiver {


    @RabbitListener(queues = {"springboot.topic.queue1"})
    @RabbitHandler
    public void consumMsg(Message message, Channel channel) throws IOException {
        System.out.println(message.getPayload());
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        System.out.println(deliveryTag);
        channel.basicAck(deliveryTag,false);
    }

}
