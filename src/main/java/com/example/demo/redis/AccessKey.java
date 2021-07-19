package com.example.demo.redis;

public class AccessKey extends BasePrefix{

    private AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

//    public static final AccessKey ACCESS = new AccessKey(5, "access");

    public static AccessKey withExpire(int expire){
        return new AccessKey(expire, "accesss");
    }
}
