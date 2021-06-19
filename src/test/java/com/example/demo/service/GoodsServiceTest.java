package com.example.demo.service;

import com.example.demo.domain.GoodsVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsServiceTest {
    @Autowired
    GoodsService goodsService;

    @Test
    public void listGoodsVo(){
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();
        for (GoodsVo goodsVo : goodsVos) {
            System.out.println(goodsVo);
        }
    }

    @Test
    public void getGoodsVoByGoodsId(){
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(1);
        System.out.println(goodsVo);
    }

    @Test
    public void reduceStock(){
        GoodsVo goodsVo = new GoodsVo();
        goodsVo.setId(1L);
        goodsService.reduceStock(goodsVo);
    }
}
