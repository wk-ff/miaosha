package com.example.demo.redis;

public class MiaoshaUserKey extends BasePrefix{

    public final static int TOKEN_EXPIRE = 3600 * 24 * 2;
    private MiaoshaUserKey(int expire, String prefix){
        super(expire, prefix);
    }

    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE, "tk" );
}
