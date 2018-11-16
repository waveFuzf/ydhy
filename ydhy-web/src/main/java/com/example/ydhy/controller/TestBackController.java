package com.example.ydhy.controller;

import com.example.ydhy.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class TestBackController {
    @Autowired
    private HttpClientUtil httpClientUtil;
    @GetMapping(value = "backTest")
    public String TestBack(){
        String string=httpClientUtil.doGet("http://192.168.22.223:8080/backTest");
        return "";
    }
}
