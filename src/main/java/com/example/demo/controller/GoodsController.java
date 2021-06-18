package com.example.demo.controller;

import com.example.demo.domain.MiaoshaUser;
import com.example.demo.service.MiaoshaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * 展示商品列表，已经商品详情页
 * @author wk-ff
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    MiaoshaUserService miaoshaUserService;

    @GetMapping("/to_list")
    public ModelAndView doLogin(Model model, MiaoshaUser user){
        model.addAttribute("user", user);
        return new ModelAndView("goods_list");
    }

//    @GetMapping("/to_detail")
//    public
}
