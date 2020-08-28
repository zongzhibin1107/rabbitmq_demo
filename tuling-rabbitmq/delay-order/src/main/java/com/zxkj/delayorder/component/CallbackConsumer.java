package com.zxkj.delayorder.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.zxkj.delayorder.bo.MsgTxtBo;
import com.zxkj.delayorder.service.IOrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class CallbackConsumer {
    public AtomicInteger c = new AtomicInteger(0);
    public static final String PRODUCT_TO_CALLBACK_QUEUE_NAME = "product_to_callback_queue";
    public static final String ORDER_DELAY_CHECK_QUEUE_NAME = "order.delay.queue";

    @Autowired
    MsgSender msgSender;
    @Autowired
    IOrderInfoService orderInfoService;

    /**
     *商品服务处理业务之后  发送消息  这个方法用户处理这个消息
     */
    @RabbitListener(queues = {PRODUCT_TO_CALLBACK_QUEUE_NAME})
    @RabbitHandler
    public void consumeProductCallbackMsg(Message message, Channel channel) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        MsgTxtBo msgTxtBo = objectMapper.readValue(message.getBody(), MsgTxtBo.class);
        log.info("收到商品服务发来的消息！"+msgTxtBo.toString());
        Long deliveryTag = (Long) message.getMessageProperties().getDeliveryTag();
        log.info("收到商品服务发来的消息！"+deliveryTag);
    }


    /**
     * 处理订单发送消息之后  延时检查任务
     * @param message
     * @param channel
     */
    @RabbitListener(queues = {ORDER_DELAY_CHECK_QUEUE_NAME})
    @RabbitHandler
    public void consumeDealyMsg(Message message, Channel channel) throws IOException {
        log.info("--------------------------------------------------------------");
        ObjectMapper objectMapper = new ObjectMapper();
        MsgTxtBo msgTxtBo = objectMapper.readValue(message.getBody(), MsgTxtBo.class);
        log.info("收到订单服务发来检验消息！"+msgTxtBo.toString());
        Long deliveryTag = (Long) message.getMessageProperties().getDeliveryTag();
        log.info("收到订单服务发来检验消息！"+deliveryTag);
        if(c.getAndIncrement()<3){
            log.info("假设没有查到！");
            orderInfoService.retryMsg(msgTxtBo);
        }else{
            log.info("成功查询到了！");
        }
    }


}
