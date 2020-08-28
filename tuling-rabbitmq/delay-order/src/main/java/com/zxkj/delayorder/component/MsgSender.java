package com.zxkj.delayorder.component;

import com.zxkj.delayorder.bo.MsgTxtBo;
import com.zxkj.delayorder.constants.MqConst;
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
    @Autowired
    private TulingMsgComfirmListener tulingMsgComfirmListener;


    /**
     * 订单服务向商品服务发送消息
     * @param msgTxtBo
     */
    public  void senderMsg(MsgTxtBo msgTxtBo){
        CorrelationData correlationData = new CorrelationData(msgTxtBo.getMsgId()+"_"+msgTxtBo.getOrderNo());
        rabbitTemplate.convertAndSend(MqConst.ORDER_TO_PRODUCT_EXCHANGE_NAME,MqConst.ORDER_TO_PRODUCT_ROUTING_KEY,msgTxtBo,correlationData);
    }


    /**
     * 向TTL的队列中放入 消息 并指定过期时间
     * @param msgTxtBo
     */
    public void senderDelayCheckMsg(MsgTxtBo msgTxtBo) {
        CorrelationData correlationData = new CorrelationData(msgTxtBo.getMsgId()+"_"+msgTxtBo.getOrderNo()+"_delay");
        rabbitTemplate.convertAndSend(MqConst.ORDER_TO_PRODUCT_TTL_EXCHANGE_NAME,MqConst.ORDER_TO_PRODUCT_TTL_ROUTING_KEY,
                msgTxtBo,correlationData);
    }



    @Override
    public void afterPropertiesSet() throws Exception {
        rabbitTemplate.setConfirmCallback(tulingMsgComfirmListener);
        //设置消息转换器
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
    }
}
