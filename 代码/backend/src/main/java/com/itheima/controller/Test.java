package com.itheima.controller;

import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Test {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/hello")
    public String hello(){
        System.out.println(userService.findByUserId(5));
        System.out.println("hello springboot");
        return "hello springboot";
    }
}
