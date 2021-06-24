package com.example.demo.controller;

import com.example.demo.domain.GoodsVo;
import com.example.demo.domain.MiaoshaUser;
import com.example.demo.redis.GoodsKey;
import com.example.demo.redis.RedisService;
import com.example.demo.result.Result;
import com.example.demo.service.GoodsService;
import com.example.demo.service.MiaoshaUserService;
import com.example.demo.vo.GoodsDetailVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 展示商品列表，已经商品详情页
 * @author wk-ff
 */
@Controller
@RequestMapping("/goods")
@SuppressWarnings("all")
public class GoodsController {
    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String  list(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                        Model model, MiaoshaUser user){
        model.addAttribute("user", user);
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsVos);
//        return "goods_list";
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        IWebContext context = new WebContext(httpServletRequest, httpServletResponse, httpServletRequest.getServletContext(), httpServletRequest.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", context);
        if(!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }


    @RequestMapping(value = "/detail/{goodsId}", method = RequestMethod.GET)
    @ResponseBody
    public Result detail(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                          Model model, MiaoshaUser user,
                          @PathVariable("goodsId")long goodsId){
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);

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

        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        goodsDetailVo.setMiaoshaStatus(miaoshaStatus);
        goodsDetailVo.setRemainSeconds(remainSeconds);
        goodsDetailVo.setUser(user);
        goodsDetailVo.setGoods(goodsVo);

        return Result.success(goodsDetailVo);
    }

    @RequestMapping(value = "/detail2/{goodsId}", produces = "text/html")
    @ResponseBody
    public String detail2(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         Model model, MiaoshaUser user,
                         @PathVariable("goodsId")long goodsId){
//        查询缓存
        String html = redisService.get(GoodsKey.getGoodsList, "" + goodsId, String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }

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

        IWebContext context = new WebContext(httpServletRequest, httpServletResponse, httpServletRequest.getServletContext(), httpServletRequest.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", context);
        if(!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsDetail, "" + goodsId, html);
        }
        return html;
    }

}
