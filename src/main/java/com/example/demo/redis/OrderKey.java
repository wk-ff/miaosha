package com.example.demo.redis;

public class OrderKey extends BasePrefix{
    public OrderKey(String prefix) {
        super(prefix);
    }

    // 根据userId,goodsId存储秒杀订单信息
    public static OrderKey getMiaoshaOrderByUidGid = new OrderKey("moug");
}
