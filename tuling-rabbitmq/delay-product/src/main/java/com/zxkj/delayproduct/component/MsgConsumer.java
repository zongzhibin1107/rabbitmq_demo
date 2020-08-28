package com.zxkj.delayproduct.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.zxkj.delayproduct.bo.MsgTxtBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class MsgConsumer {

    public static final String  ORDER_TO_PRODUCT_QUEUE_NAME =  "order-to-product.queue";

    @Autowired
    MsgSender msgSender;

    @RabbitListener(queues = {ORDER_TO_PRODUCT_QUEUE_NAME})
    @RabbitHandler
    public void consumOrderMsg(Message message, Channel channel) throws IOException {
        log.info("--------------------------------------------------------------");
        ObjectMapper objectMapper = new ObjectMapper();
        MsgTxtBo msgTxtBo = objectMapper.readValue(message.getBody(), MsgTxtBo.class);
        log.info("收到订单服务发来的消息！"+msgTxtBo.toString());
        Long deliveryTag = (Long) message.getMessageProperties().getDeliveryTag();
        log.info("收到订单服务发来的消息！"+deliveryTag);
        channel.basicAck(deliveryTag,false);
        msgSender.senderMsg(msgTxtBo);
        log.info("发送确认消息！");
    }
}
