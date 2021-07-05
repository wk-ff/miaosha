package com.example.demo.redis;

public class MiaoshaKey extends BasePrefix{

    private MiaoshaKey(String prefix){
        super(prefix);
    }

    public static final MiaoshaKey isGoodsOver = new MiaoshaKey("go");
}
