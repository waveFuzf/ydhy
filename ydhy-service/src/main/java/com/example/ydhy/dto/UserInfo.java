package com.example.ydhy.dto;

import lombok.Data;

@Data
public class UserInfo {
    private Integer id;

    private String loginName;

    private String realName;

    private String phone;

    private String email;

    private Integer deptId;

    private String isSuper;

    private String vchatNum;
}
