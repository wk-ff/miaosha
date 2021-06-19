package com.example.demo.service;

import com.example.demo.dao.MiaoshaOrderDao;
import com.example.demo.domain.MiaoshaOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MiaoshaOrderService {
    @Autowired
    MiaoshaOrderDao miaoshaOrderDao;

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId){
        return miaoshaOrderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
    }
}
