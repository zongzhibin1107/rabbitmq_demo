package com.zxkj.delayorder.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zxkj.delayorder.bo.MsgTxtBo;
import com.zxkj.delayorder.component.MsgSender;
import com.zxkj.delayorder.entiy.MessageContent;
import com.zxkj.delayorder.entiy.OrderInfo;
import com.zxkj.delayorder.mapper.OrderInfoMapper;
import com.zxkj.delayorder.service.IOrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class OrderInfoServiceImpl implements IOrderInfoService {


    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private MsgSender msgSender;

    @Transactional
    @Override
    public void saveOrderInfo(OrderInfo orderInfo) {
        try {
            orderInfoMapper.saveOrderInfo(orderInfo);
        }catch (Exception e) {
            log.error("操作数据库失败:{}",e);
            throw new RuntimeException("操作数据库失败");
        }
    }

    /**
     * 订单保存 并发送相关消息
     * @param orderInfo
     */
    @Override
    public void saveOrderInfoWithMessage(OrderInfo orderInfo) {
        log.info("订单成功保存了！！！！");
        /**发送消息  */
        String msgId = UUID.randomUUID().toString();
        MsgTxtBo msgTxtBo = new MsgTxtBo();
        msgTxtBo.setMsgId(msgId);
        msgTxtBo.setOrderNo(orderInfo.getOrderNo());
        msgTxtBo.setProductNo(orderInfo.getProductNo());
        //todo 这里如果报异常怎么办？？？
        /** 通知商品服务*/
        msgSender.senderMsg(msgTxtBo);
        /** 添加延时消息*/
        msgSender.senderDelayCheckMsg(msgTxtBo);
    }



    public void retryMsg(MsgTxtBo msgTxtBo){
        log.info("消息重新发送:{}",msgTxtBo);
        //第一次发送消息
        msgSender.senderMsg(msgTxtBo);
        msgSender.senderDelayCheckMsg(msgTxtBo);
    }


}
