package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.redis.MiaoshaKey;
import com.example.demo.redis.RedisService;
import com.example.demo.util.MD5Util;
import com.example.demo.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Random;

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

    public boolean checkPath(Long userId, String path, long goodsId) {
        if(path == null){
            return false;
        }
        String pathOld = redisService.get(MiaoshaKey.getMiaoshaPath, "" + userId + "_" + goodsId, String.class);
        return path.equals(pathOld);
    }

    public String createPath(Long userId, long goodsId) {
        String str = MD5Util.md5(UUIDUtil.uuid() + "123456");
        redisService.set(MiaoshaKey.getMiaoshaPath, "" + userId + "_" +goodsId, str);
        return str;
    }

    public BufferedImage createVerifyCode(Long userId, long goodsId) {
        if(goodsId <= 0){
            return null;
        }
        int width = 80;
        int height = 32;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        // 设置背景颜色
        graphics.setColor(new Color(0xDCDCDC));
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.black);
        graphics.drawRect(0, 0, width - 1, height - 1);
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            graphics.drawOval(x, y, 0, 0);
        }
        String verifyCode = generateVerifyCode(random);
        graphics.setColor(new Color(0, 100, 0));
        graphics.setFont(new Font("Candara", Font.BOLD, 24));
        graphics.drawString(verifyCode, 8, 24);
        graphics.dispose();

        // 验证码保存到redis
        int rnd = calc(verifyCode);
        redisService.set(MiaoshaKey.getVerifyCode, "" + userId + "_" + goodsId, rnd);
        return image;
    }

    private int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer) engine.eval(exp);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    private static final char[] OPERATIONS = new char[]{'+', '-', '*'};

    private String generateVerifyCode(Random random) {
        int num1 = random.nextInt(10);
        int num2 = random.nextInt(10);
        int num3 = random.nextInt(10);
        char operation1 = OPERATIONS[random.nextInt(3)];
        char operation2 = OPERATIONS[random.nextInt(3)];
        String exp = "" + num1 + operation1 + num2 + operation2 + num3;
        return exp;
    }

    public boolean checkVerifyCode(Long userId, long goodsId, int verifyCode) {
        if(goodsId <= 0){
            return false;
        }
        Integer codeOld = redisService.get(MiaoshaKey.getVerifyCode, "" + userId + "_" + goodsId, Integer.class);
        if(codeOld == null || codeOld - verifyCode != 0){
            return false;
        }
        redisService.delete(MiaoshaKey.getVerifyCode, "" + userId + "_" + goodsId);
        return true;
    }
}
