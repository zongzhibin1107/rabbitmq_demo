package com.zxkj.delayproduct.component;

import com.zxkj.delayproduct.bo.MsgTxtBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MsgSender implements InitializingBean {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    public static final String ORDER_TO_PRODUCT_EXCHANGE_NAME = "order-to-product.exchange";
    public static final String PRODUCT_TO_CALLBACK_ROUTING_KEY = "product_to_callback_key";

    /**
     * 方法实现说明:真正的发送消息
     * @author:smlz
     * @param msgTxtBo:发送的消息对象
     * @return:
     * @exception:
     * @date:2019/10/11 20:01
     */
    public  void senderMsg(MsgTxtBo msgTxtBo){
        CorrelationData correlationData = new CorrelationData(msgTxtBo.getMsgId()+"_"+msgTxtBo.getOrderNo());
        rabbitTemplate.convertAndSend(ORDER_TO_PRODUCT_EXCHANGE_NAME,PRODUCT_TO_CALLBACK_ROUTING_KEY,msgTxtBo,correlationData);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //设置消息转换器
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
    }
}
