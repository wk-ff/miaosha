package com.example.demo.service;

import com.example.demo.domain.Goods;
import com.example.demo.domain.GoodsVo;
import com.example.demo.domain.MiaoshaUser;
import com.example.demo.domain.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaService {
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goodsVo){
        goodsService.reduceStock(goodsVo);

        return orderService.createOrder(user, goodsVo);
    }

}
