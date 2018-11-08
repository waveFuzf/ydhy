package com.example.ydhy.service;

import com.example.ydhy.dao.UserMapper;
import com.example.ydhy.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service("demoService")
public class DemoServiceImpl implements DemoService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User getSomething() {

        Example e=new Example(User.class);
        e.createCriteria().getAllCriteria();
        List<User> user=userMapper.selectByExample(e);
        User user2=new User();
        user2.setId(1);
        User user1=userMapper.selectByPrimaryKey(user2);
        return user.get(0);

    }
}
