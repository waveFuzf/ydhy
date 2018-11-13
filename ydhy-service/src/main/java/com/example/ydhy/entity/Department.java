package com.example.ydhy.entity;

import com.example.ydhy.dto.DeptInfo;
import lombok.Data;

import javax.persistence.Id;
import java.util.Date;
@Data
public class Department {
    @Id
    private Integer id;

    private String deptName;

    private String introdecu;

    private String phone;

    private Integer directorId;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String isDelete;

    public Department(){

    }

    public Department(DeptInfo deptInfo) {
        this.deptName=deptInfo.getDeptName();
        this.id=deptInfo.getId();
        this.introdecu=deptInfo.getIntrodecu();
        this.phone=deptInfo.getPhone();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getIntrodecu() {
        return introdecu;
    }

    public void setIntrodecu(String introdecu) {
        this.introdecu = introdecu == null ? null : introdecu.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
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

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
    }
}