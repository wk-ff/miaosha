package com.example.demo.rabbitmq;

import com.example.demo.domain.GoodsVo;
import com.example.demo.domain.MiaoshaOrder;
import com.example.demo.domain.MiaoshaUser;
import com.example.demo.domain.OrderInfo;
import com.example.demo.redis.RedisService;
import com.example.demo.result.CodeMsg;
import com.example.demo.result.Result;
import com.example.demo.service.GoodsService;
import com.example.demo.service.MiaoshaService;
import com.example.demo.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    public static Logger logger = LoggerFactory.getLogger(MQReceiver.class);

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receive(String message){
        logger.info("receive message:" + message);
        MiaoshaMessage miaoshaMessage = RedisService.stringToBean(message, MiaoshaMessage.class);
        MiaoshaUser user = miaoshaMessage.getUser();
        long goodsId = miaoshaMessage.getGoodsId();

        // 判断商品库存
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.getGoodsStock();
        if(stock <= 0){
            return;
        }

        // 判断是否秒杀成功
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(miaoshaOrder != null){
            return;
        }

        // 减库存，下订单，写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goodsVo);

    }

//    @RabbitListener(queues = MQConfig.QUEUE)
//    public void receive(String message){
//        logger.info("receive message:" + message);
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
//    public void receiveTopic1(String message){
//        logger.info("receive topic queue1 message:" + message);
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
//    public void receiveTopic2(String message){
//        logger.info("receive topic queue2 message:" + message);
//    }
//
//    @RabbitListener(queues = MQConfig.HEADER_QUEUE)
//    public void receiveHeaderQueue(byte[] message){
//        logger.info("receive header queue message:" + new String(message));
//    }
}
