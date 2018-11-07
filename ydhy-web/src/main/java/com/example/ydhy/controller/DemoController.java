package com.example.ydhy.controller;

import com.example.ydhy.entity.User;
import com.example.ydhy.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class DemoController {
    @Autowired
    private DemoService demoService;
    @GetMapping(value = "/demo")
    public User DemoTest(){
        return demoService.getSomething();
    }
}

