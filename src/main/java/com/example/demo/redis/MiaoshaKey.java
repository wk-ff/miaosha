package com.example.demo.redis;

public class MiaoshaKey extends BasePrefix{

    private MiaoshaKey(int expireSeconds, String prefix){
        super(expireSeconds, prefix);
    }

    public static final MiaoshaKey isGoodsOver = new MiaoshaKey(0,"go");
    public static final MiaoshaKey getMiaoshaPath = new MiaoshaKey(60, "mp");
    public static final MiaoshaKey getVerifyCode = new MiaoshaKey(300, "vc");
}
