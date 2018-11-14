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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
    }

    public String getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(String isSuper) {
        this.isSuper = isSuper == null ? null : isSuper.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getVchatNum() {
        return vchatNum;
    }

    public void setVchatNum(String vchatNum) {
        this.vchatNum = vchatNum == null ? null : vchatNum.trim();
    }
}