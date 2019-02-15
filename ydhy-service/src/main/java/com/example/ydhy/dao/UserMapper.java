package com.example.ydhy.dao;

import com.example.ydhy.entity.User;

import java.util.List;

import com.example.ydhy.tkMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserMapper extends tkMapper<User>{
    @Update({
            "update user set status=#{status} where is_delete='0' and login_name=#{username}"
    })
    void changeStatusByname(@Param("username") String username,@Param("status") String status);
    @Select({
            "select u.id,u.login_name,u.real_name,u.phone,u.email,u.dept_id,d.dept_name," +
                    "u.vchat_num,u.position,u.is_super,u.status from user u LEFT JOIN department d ON u.dept_id=d.id" +
                    " and u.is_delete !='1'"
    })
    @Results({
            @Result(column="dept_name", property="deptName"),
    })
    List<User> getAllUser();
//    @Update({
//        "update user set status='1' where is_delete='0' and login_name=#{username,jdbcType=VARCHAR}"
//    })
//    void changeStatusByname(String username,String status);
}