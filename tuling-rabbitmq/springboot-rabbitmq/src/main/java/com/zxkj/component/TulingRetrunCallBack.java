package com.zxkj.component;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class TulingRetrunCallBack implements RabbitTemplate.ReturnCallback{


    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println(message.getMessageProperties().getCorrelationId());
        System.out.println(replyText);
        System.out.println(new String(message.getBody()));
        System.out.println(replyCode);
        System.out.println(exchange);
        System.out.println(routingKey);
        System.out.println("消息不可达");
    }
}
