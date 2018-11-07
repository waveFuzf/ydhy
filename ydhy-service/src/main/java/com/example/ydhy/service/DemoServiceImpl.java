package com.example.ydhy.service;

import com.example.ydhy.dao.UserMapper;
import com.example.ydhy.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("demoService")
public class DemoServiceImpl implements DemoService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User getSomething() {
        User user=userMapper.selectByPrimaryKey(1);
        return user;
    }
}
