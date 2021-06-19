package com.example.demo.service;

import com.example.demo.domain.GoodsVo;
import com.example.demo.domain.MiaoshaUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MiaoshaServiceTest {
    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    GoodsService goodsService;

    @Test
    public void create(){
        MiaoshaUser user = miaoshaUserService.getById(13053055157L);
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(2L);

        miaoshaService.miaosha(user, goodsVo);
    }
}
