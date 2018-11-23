package com.example.ydhy.service;

import com.example.ydhy.dto.UserInfo;
import com.example.ydhy.entity.User;

import java.util.List;

public interface UserService {
    Boolean save(User user);

    User getByUsername(String username);

    void changeStatusByname(String status, String username);

    void updateUserInfo(UserInfo userInfo);

    Integer deleteByUserId(Integer userId);

    User getByUserId(Integer id);

    List<User> getUsersByName(String name, Integer pageSize, Integer pageNo);

    List<User> getUsers(Integer pageSize, Integer pageNo);
}
