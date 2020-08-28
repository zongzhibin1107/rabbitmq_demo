package com.zxkj.delayorder.constants;

/**
* @vlog: 高于生活，源于生活
* @desc: 类的描述:
* @author: smlz
* @createDate: 2019/10/11 16:41
* @version: 1.0
*/
public class MqConst {

    public static final String ORDER_TO_PRODUCT_EXCHANGE_NAME = "order-to-product.exchange";
    public static final String ORDER_TO_PRODUCT_QUEUE_NAME = "order-to-product.queue";
    public static final String ORDER_TO_PRODUCT_ROUTING_KEY = "order-to-product.key";



    public static final String ORDER_DELAY_CHECK_EXCHANGE_NAME = "order.delay.exchange";
    public static final String ORDER_DELAY_CHECK_QUEUE_NAME = "order.delay.queue";
    public static final String ORDER_DELAY_CHECK_ROUTING_KEY = "order.delay.key";



    public static final String ORDER_TO_PRODUCT_TTL_EXCHANGE_NAME = "order.to.product.ttl.exchange";
    public static final String ORDER_TO_PRODUCT_TTL_QUEUE_NAME = "order.to.product.ttl.queue";
    public static final String ORDER_TO_PRODUCT_TTL_ROUTING_KEY = "order.to.product.ttl.key";


    public static final String PRODUCT_TO_CALLBACK_QUEUE_NAME = "product_to_callback_queue";
    public static final String PRODUCT_TO_CALLBACK_ROUTING_KEY = "product_to_callback_key";



    /**队列名称*/



    /**路由key*/


    public static final String ORDER_TO_PRODUCT_DELAY_EXCHANGE_NAME = "order-to-product.delayexchange";

    public static final String ORDER_TO_PRODUCT_DELAY_QUEUE_NAME = "order-to-product.delayqueue";

    public static final String ORDER_TO_PRODUCT_DELAY_ROUTING_KEY = "order-to-product.delaykey";

    public static final Integer DELAY_TIME = 30000;

    /**消息重发的最大次数*/
    public static final Integer MSG_RETRY_COUNT = 5;

    public static final Integer TIME_DIFF = 100;
}
