package com.example.demo.redis;

import lombok.AllArgsConstructor;

public abstract class BasePrefix implements KeyPrefix{
    private int expireSeconds;

    private String prefix;

    public BasePrefix(String prefix){
        this.expireSeconds = 0;
        this.prefix = prefix;
    }

    public BasePrefix(int expireSeconds, String prefix){
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    // 默认0永不过期
    @Override
    public int expireSeconds() {
        return this.expireSeconds;
    }

    @Override
    public String getPrefix(){
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }

}
