package com.example.demo.service;

import com.example.demo.dao.GoodsDao;
import com.example.demo.domain.GoodsVo;
import com.example.demo.domain.MiaoshaGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo(){
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    public boolean reduceStock(@NotNull GoodsVo goodsVo){
        MiaoshaGoods miaoshaGoods = new MiaoshaGoods();
        miaoshaGoods.setGoodsId(goodsVo.getId());
        int res = goodsDao.reduceStock(miaoshaGoods);
        return res > 0;
    }
}
