package com.example.demo.controller;

import com.example.demo.domain.GoodsVo;
import com.example.demo.domain.MiaoshaOrder;
import com.example.demo.domain.MiaoshaUser;
import com.example.demo.domain.OrderInfo;
import com.example.demo.rabbitmq.MQSender;
import com.example.demo.rabbitmq.MiaoshaMessage;
import com.example.demo.redis.GoodsKey;
import com.example.demo.redis.RedisService;
import com.example.demo.result.CodeMsg;
import com.example.demo.result.Result;
import com.example.demo.service.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {
    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    MQSender sender;

    private Map<Long, Boolean> localOverMap = new HashMap<>();

    @RequestMapping(value = "/do_miaosha", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> miaosha(Model model, MiaoshaUser user, @RequestParam("goodsId")long goodsId){
        model.addAttribute("user", user);
        if(user == null){
//            return new ModelAndView("login");
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        // 内存标记
        if(localOverMap.get(goodsId)){
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }

        // 预减库存
        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);
        if(stock < 0){
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }

        // 判断是否秒杀成功
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(miaoshaOrder != null){
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        // 入队
        MiaoshaMessage miaoshaMessage = new MiaoshaMessage();
        miaoshaMessage.setUser(user);
        miaoshaMessage.setGoodsId(goodsId);
        sender.sendMiaoshaMessage(miaoshaMessage);

        // 排队中
        return Result.success(0);

        /*
        // 判断商品库存
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.getGoodsStock();
        if(stock <= 0){
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }

        // 判断是否秒杀成功
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(miaoshaOrder != null){
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        // 减库存，下订单，写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goodsVo);

        return Result.success(orderInfo);

         */
    }

    /**
     *
     * @param model
     * @param user
     * @param goodsId
     * @return orderId 成功
     *          -1 秒杀失败
     *          0 排队中
     */
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> miaoshaResult(Model model, MiaoshaUser user, @RequestParam("goodsId")long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
//            return new ModelAndView("login");
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result = miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return Result.success(result);
    }

    /**
     * 系统初始化
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        if(goodsVoList == null || goodsVoList.size() == 0){
            return;
        }

        for (GoodsVo goodsVo : goodsVoList) {
            redisService.set(GoodsKey.getMiaoshaGoodsStock, "" + goodsVo.getId(), goodsVo.getStockCount());
            localOverMap.put(goodsVo.getId(), false);
        }
    }
}
