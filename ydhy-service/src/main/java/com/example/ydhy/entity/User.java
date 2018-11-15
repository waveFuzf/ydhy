package com.example.ydhy.entity;

import com.example.ydhy.dto.UserInfo;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
@Data
public class User implements Serializable {

    @Id
    private Integer id;

    private String loginName;

    private String realName;

    private String phone;

    private String email;

    private Integer deptId;

    private String password;

    private String isDelete;

    private String isSuper;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String vchatNum;

    private String position;

    public User(String username, String password) {
        this.loginName=username;
        this.password=password;
    }
    public User(){

    }

    public User(UserInfo userInfo) {
        this.id=userInfo.getId();
        this.phone=userInfo.getPhone();
        this.email=userInfo.getEmail();
        this.deptId=userInfo.getDeptId();
        this.vchatNum=userInfo.getVchatNum();
        this.realName=userInfo.getRealName();
    }
}