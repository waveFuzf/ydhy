package com.example.ydhy.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class TestBackController {
    @GetMapping(value = "backTest",params = "action=backTest")
    public String TestBack(){
        return "testBack接口OK";
    }
}
