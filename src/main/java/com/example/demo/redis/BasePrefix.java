package com.example.demo.redis;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class BasePrefix implements KeyPrefix{
    private int expireSeconds;

    private String prefix;

    // 默认0永不过期
    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix(){
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }

}
