package com.example.demo.rabbitmq;

import com.example.demo.domain.MiaoshaUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MiaoshaMessage {
    private MiaoshaUser user;
    private long goodsId;
}
