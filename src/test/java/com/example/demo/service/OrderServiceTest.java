package com.example.demo.service;


import com.example.demo.domain.GoodsVo;
import com.example.demo.domain.MiaoshaOrder;
import com.example.demo.domain.MiaoshaUser;
import com.example.demo.domain.OrderInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {
    @Autowired
    OrderService orderService;

    @Test
    public void getGoodsVoByUserIdGoodsId(){
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(18912341234L, 1L);
        System.out.println(order);
    }

    @Test
    public void createOrder(){
        MiaoshaUser user = new MiaoshaUser();
        GoodsVo goodsVo = new GoodsVo();
        user.setId(13053055157L);
        goodsVo.setId(1L);
        goodsVo.setGoodsName("iphoneX");
        goodsVo.setMiaoshaPrice(0.01);
        orderService.createOrder(user, goodsVo);

    }
}
