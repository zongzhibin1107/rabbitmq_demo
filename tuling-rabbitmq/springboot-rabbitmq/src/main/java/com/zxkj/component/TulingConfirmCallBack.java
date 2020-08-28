package com.zxkj.component;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class TulingConfirmCallBack implements RabbitTemplate.ConfirmCallback {


    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println(correlationData.getId());
        System.out.println(ack);
        if(ack){
            System.out.println("ack");
        }else{
            System.out.println("nack");
        }
    }
}
