package com.example.ydhy.service;

import com.example.ydhy.dao.UserMapper;
import com.example.ydhy.dto.UserInfo;
import com.example.ydhy.entity.User;
import com.example.ydhy.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Boolean save(User user) {
        user.setCreateTime(new Date());
        user.setIsDelete("0");
        user.setStatus("0");
        user.setIsSuper("0");
        userMapper.insert(user);
        return true;
    }

    @Override
    public User getByUsername(String username) {
        Example e=new Example(User.class);
        e.createCriteria().andEqualTo("loginName",username).andEqualTo("isDelete","0");
        List<User> checkUser=userMapper.selectByExample(e);
        if (checkUser.size()==0){
            return null;
        }
        return checkUser.get(0);
    }

    @Override
    public void changeStatusByname(String username) {
        userMapper.changeStatusByname(username);
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        User user=new User(userInfo);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void deleteByUserId(Integer userId) {
        User user=new User();
        user.setId(userId);
        user.setIsDelete("1");
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User getByUserId(Integer id) {
        Example e=new Example(User.class);
        e.createCriteria().andEqualTo("id",id).andEqualTo("isDelete","0");
        List<User> checkUser=userMapper.selectByExample(e);
        if (checkUser.size()==0){
            return null;
        }
        return checkUser.get(0);
    }

}
