package com.example.ydhy.entity;

import com.example.ydhy.dto.DeptInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
@Data
public class Department {
    @Id
    private Integer id;
    @Column(name = "dept_name")
    private String deptName;
    @Column(name = "introdecu")
    private String introdecu;
    @Column(name = "phone")
    private String phone;
    @Column(name = "director_id")
    private Integer directorId;
    @Column(name = "status")
    private String status;
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
    @Column(name = "is_delete")
    private String isDelete;
    @Transient
    private String directorName;

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