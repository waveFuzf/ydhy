package com.example.ydhy.service;

import com.example.ydhy.dao.UserMapper;
import com.example.ydhy.dto.UserInfo;
import com.example.ydhy.entity.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
    public void changeStatusByname(String status, String username) {
        userMapper.changeStatusByname(username,status);
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        User user=new User(userInfo);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public Integer deleteByUserId(Integer userId) {
        User user=new User();
        user.setId(userId);
        user.setIsDelete("1");
        return userMapper.updateByPrimaryKeySelective(user);
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

    @Override
    public List<User> getUsersByName(String name, Integer pageSize, Integer pageNo) {
        Page<User> pageInfo = PageHelper.startPage(pageNo, pageSize);
        Example e=new Example(User.class);
        e.createCriteria().andLike("realName","%"+name+"%")
                .andEqualTo("isDelete","0");
        List<User> user=userMapper.selectByExample(e);
        return pageInfo;
    }

    @Override
    public List<User> getUsers(Integer pageSize, Integer pageNo) {
        if (pageSize==null&&pageNo==null){
            List<User> user=userMapper.getAllUser();
            return user;
        }
        Page<User> pageInfo = PageHelper.startPage(pageNo, pageSize);
        List<User> user=userMapper.getAllUser();
        return pageInfo;
    }

    @Override
    public List<User> getUsersByName(String name) {
        Example e=new Example(User.class);
        e.createCriteria().andLike("realName","%"+name+"%")
                .andEqualTo("isDelete","0");
        List<User> user=userMapper.selectByExample(e);
        return user;
    }

    @Override
    public List<User> getDirectors() {
        Example e=new Example(User.class);
        e.createCriteria().andEqualTo("position","主管");
        return userMapper.selectByExample(e);
    }

}
