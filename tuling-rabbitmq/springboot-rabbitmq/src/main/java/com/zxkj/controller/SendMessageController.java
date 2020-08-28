package com.zxkj.controller;

import com.zxkj.component.MsgSender;
import com.zxkj.config.RabbitmqConfig;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class SendMessageController {

    @Autowired
    MsgSender msgSender;

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello!";
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",new Date());
        msgSender.sendMsg(messageData,map);
        return "ok";
    }

    @GetMapping("/sendDirectMessage1")
    public String sendDirectMessage1() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello!";
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",new Date());
        msgSender.sendMsg2(messageData,map);
        return "ok";
    }
    @GetMapping("/sendDirectMessage2")
    public String sendDirectMessage2() {
        msgSender.sendMsg(RabbitmqConfig.DELAY_QUEUE_NAME,new StringBuilder("22233333"));
        return "ok";
    }




}
