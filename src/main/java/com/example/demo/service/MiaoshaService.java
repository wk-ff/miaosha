package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.redis.MiaoshaKey;
import com.example.demo.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaService {
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goodsVo){
        boolean success = goodsService.reduceStock(goodsVo);
        if(success){
            return orderService.createOrder(user, goodsVo);
        }else{
            setGoodsOver(goodsVo.getId());
            return null;
        }
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver, "" + goodsId, true);
    }

    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        if(order != null){
            return order.getOrderId();
        }else{
            boolean isOver = getGoodsOver(goodsId);
            if(isOver){
                return -1;
            }else{
                return 0;
            }
        }
    }

    private boolean getGoodsOver(long goodsId) {
         return redisService.exists(MiaoshaKey.isGoodsOver, "" + goodsId);
    }
}
