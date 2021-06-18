package com.example.demo.controller;

import com.example.demo.result.CodeMsg;
import com.example.demo.result.Result;
import com.example.demo.service.MiaoshaUserService;
import com.example.demo.util.ValidatorUtil;
import com.example.demo.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**登录控制器
 * @author wk-ff
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    MiaoshaUserService miaoshaUserService;

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/tologin")
    public ModelAndView toLogin(){
        return new ModelAndView("login");
    }

    @PostMapping("/dologin")
    public Result<Boolean> doLogin(HttpServletResponse httpServletResponse, @Valid LoginVo loginVo){
        logger.info(loginVo.toString());
        // 登录
        miaoshaUserService.login(httpServletResponse, loginVo);
        return Result.success(true);

    }
}
