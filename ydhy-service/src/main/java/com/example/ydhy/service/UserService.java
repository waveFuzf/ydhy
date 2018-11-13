package com.example.ydhy.service;

import com.example.ydhy.dto.UserInfo;
import com.example.ydhy.entity.User;

public interface UserService {
    Boolean save(User user);

    User getByUsername(String username);

    void changeStatusByname(String username);

    void updateUserInfo(UserInfo userInfo);

    void deleteByUserId(Integer userId);
}
