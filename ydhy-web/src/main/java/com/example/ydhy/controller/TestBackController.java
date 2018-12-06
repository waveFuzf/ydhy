package com.example.ydhy.controller;

import com.example.ydhy.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class TestBackController {
    @Autowired
    private HttpClientUtil httpClientUtil;
    @GetMapping(value = "backTest/{count}")
    public String TestBack(@PathVariable("count") Integer count){
        if (count<10) {
            count=count+1;
            String string = httpClientUtil.doGet("http://192.168.22.223:8080/backTest/" + count);
            return ""+count;
        }
        return ""+count;
    }
}
