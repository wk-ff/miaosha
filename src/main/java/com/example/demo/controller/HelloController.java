package com.example.demo.controller;


import com.example.demo.domain.User;
import com.example.demo.redis.RedisService;
import com.example.demo.redis.UserKey;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/demo")
public class HelloController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @GetMapping("/test")
    public ModelAndView test(Model model){
        model.addAttribute("name", "wk");
        return new ModelAndView("hello");
    }

    @GetMapping("/db/get")
    public String dbGet(){
        User user = userService.getById(1);

        return user.toString();
    }

    @GetMapping("/db/tx")
    public Boolean tx(){
        userService.tx();
        return true;
    }

    @GetMapping("/redis/get")
    public String redisGet(){
        return redisService.get(UserKey.getById,"" + 1, String.class);
    }

    @GetMapping("/redis/set")
    public boolean redisSet(){
        User user = new User();
        user.setId(1);
        user.setName("1111");
        redisService.set(UserKey.getById, String.valueOf(user.getId()), user.getName());
        return true;
    }
}
