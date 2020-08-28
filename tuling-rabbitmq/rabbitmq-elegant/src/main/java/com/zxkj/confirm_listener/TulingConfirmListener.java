package com.zxkj.confirm_listener;

import com.rabbitmq.client.ConfirmListener;

import java.io.IOException;

public class TulingConfirmListener implements ConfirmListener {


    @Override
    public void handleAck(long deliveryTag, boolean multiple) throws IOException {
        System.out.println("当前时间:"+System.currentTimeMillis()+"TulingConfirmListener handleAck:"+deliveryTag);
    }

    /**
     * 处理异常
     * @param deliveryTag
     * @param multiple
     * @throws IOException
     */
    @Override
    public void handleNack(long deliveryTag, boolean multiple) throws IOException {
        System.out.println("TulingConfirmListener handleNack:"+deliveryTag);
    }
}
