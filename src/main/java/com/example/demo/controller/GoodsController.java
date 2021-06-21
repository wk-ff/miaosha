package com.example.demo.controller;

import com.example.demo.domain.GoodsVo;
import com.example.demo.domain.MiaoshaUser;
import com.example.demo.redis.RedisService;
import com.example.demo.service.GoodsService;
import com.example.demo.service.MiaoshaUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 展示商品列表，已经商品详情页
 * @author wk-ff
 */
@RestController
@RequestMapping("/goods")
@SuppressWarnings("all")
public class GoodsController {
    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/to_list")
    public ModelAndView list(ModelAndView model, MiaoshaUser user){

        model.addObject("user", user);
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();
        model.addObject("goodsList", goodsVos);
        model.setViewName("goods_list");
        return model;
    }

    @RequestMapping("/to_detail/{goodsId}")
    public ModelAndView doLogin(Model model, MiaoshaUser user,
                                @PathVariable("goodsId")long goodsId){
        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goodsVo);

        long startDate = goodsVo.getStartDate().getTime();
        long endDate = goodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;
        // 秒杀尚未开始
        if(now < startDate){
            remainSeconds = (int)((startDate - now) / 1000);
        // 秒杀已经结束
        }else if(now > endDate){
            miaoshaStatus = 2;
        }else{              // 秒杀中
            miaoshaStatus = 1;
        }

        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        return new ModelAndView("goods_detail");
    }

}
