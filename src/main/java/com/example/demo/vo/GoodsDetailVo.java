package com.example.demo.vo;

import com.example.demo.domain.GoodsVo;
import com.example.demo.domain.MiaoshaUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class GoodsDetailVo {
    private int miaoshaStatus = 0;
    private int remainSeconds = 0;
    private GoodsVo goods;
    private MiaoshaUser user;
}
