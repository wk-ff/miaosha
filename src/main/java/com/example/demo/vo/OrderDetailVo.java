package com.example.demo.vo;

import com.example.demo.domain.GoodsVo;
import com.example.demo.domain.OrderInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OrderDetailVo {
    private GoodsVo goods;
    private OrderInfo order;
}
