package com.example.ydhy.dao;

import com.example.ydhy.entity.User;
import com.example.ydhy.entity.UserExample;
import java.util.List;

import com.example.ydhy.tkMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface UserMapper extends tkMapper<User>{
    @Update({
        "update user set status='0' where is_delete='0' and login_name=#{username,jdbcType=VARCHAR}"
    })
    void changeStatusByname(String username);
}