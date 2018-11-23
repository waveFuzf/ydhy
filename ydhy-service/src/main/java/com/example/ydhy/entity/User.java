package com.example.ydhy.entity;

import com.example.ydhy.dto.UserInfo;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
@Data
public class User implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "login_name")
    private String loginName;
    @Column(name = "real_name")
    private String realName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "dept_id")
    private Integer deptId;
    @Column(name = "password")
    private String password;
    @Column(name = "is_delete")
    private String isDelete;
    @Column(name = "is_super")
    private String isSuper;
    @Column(name = "status")
    private String status;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "vchat_num")
    private String vchatNum;
    @Column(name = "position")
    private String position;
    @Transient
    private String deptName;

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