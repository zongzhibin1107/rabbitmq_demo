package com.zxkj.component;

import com.zxkj.config.RabbitmqConfig;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class MsgSender implements InitializingBean {
    @Autowired
    private RabbitTemplate rabbitTemplate;



    /**
     * 测试发送我们的消息
     * @param msg 消息内容
     * @param msgProp 消息属性
     */
    public void sendMsg(Object msg, Map<String,Object> msgProp) {

        MessageHeaders messageHeaders = new MessageHeaders(msgProp);
        Message message = MessageBuilder.createMessage(msg,messageHeaders);

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend("springboot.topic.exchange","springboot.key.#",
                message,correlationData);
    }

    public void sendMsg2(Object msg, Map<String,Object> msgProp) {

        MessageHeaders messageHeaders = new MessageHeaders(msgProp);
        Message message = MessageBuilder.createMessage(msg,messageHeaders);

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend("springboot.topic.exchange","springboot.key.3",
                message,correlationData);
    }
    public void sendMsg3(Object msg, Map<String,Object> msgProp) {

        MessageHeaders messageHeaders = new MessageHeaders(msgProp);
        Message message = MessageBuilder.createMessage(msg,messageHeaders);

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        //表示为延时消息
        rabbitTemplate.convertAndSend("springboot.delay.exchange","springboot.delay.key", message, new MessagePostProcessor() {
            @Override
            public org.springframework.amqp.core.Message postProcessMessage(org.springframework.amqp.core.Message message) throws AmqpException {
                message.getMessageProperties().setHeader("x-delay",  30000);//设置延迟时间
                return message;
            }
        },correlationData);
    }


    public void sendMsg(String queueName, StringBuilder msg) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        msg.append(", 发送时间：");
        msg.append(sdf.format(new Date()));
        rabbitTemplate.convertAndSend(RabbitmqConfig.DELAY_EXCHANGE_NAME, RabbitmqConfig.DELAY_ROUTING_KEY, msg.toString(), new MessagePostProcessor() {
            @Override
            public org.springframework.amqp.core.Message postProcessMessage(org.springframework.amqp.core.Message message) throws AmqpException {
                message.getMessageProperties().setHeader("x-delay", 5000);
                return message;
            }
        });
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        //开启确认模式
        rabbitTemplate.setConfirmCallback(new TulingConfirmCallBack());
        //开启消息可达监听
        rabbitTemplate.setReturnCallback(new TulingRetrunCallBack());
        //设置消息转换器
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
    }
}
