package com.example.ydhy.controller;


import com.example.ydhy.Re.Result;
import com.example.ydhy.Re.ResultGenerator;
import com.example.ydhy.dao.UserMapper;
import com.example.ydhy.dto.UserInfo;
import com.example.ydhy.entity.User;
import com.example.ydhy.service.UserService;
import com.example.ydhy.util.ResponseUtil;
import com.example.ydhy.util.TokenUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.JsonObject;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenUtil tokenUtil;

    @GetMapping("getAll")
    public List<User> getAll(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        Example e=new Example(User.class);
        e.createCriteria().getAllCriteria();
        List<User> user=userMapper.selectByExample(e);
        return user;
    }
    @PostMapping("select")
    public List<User> select(@Param("name")String name){
        Example e=new Example(User.class);
        e.createCriteria().andLike("realName","%"+name+"%");
        List<User> user=userMapper.selectByExample(e);
        return user;
    }

    @GetMapping("/itemsPage")
    public List<User> itemsPage(int page,int size){
        Page<User> pageInfo = PageHelper.startPage(page, size);
        List<User> users = userMapper.selectAll();
        return pageInfo;
    }

    @PostMapping("/userInfo")
    public Result updateUserInfo(@ApiParam(value = "用户信息")@RequestBody UserInfo userInfo){
        try {
            userService.updateUserInfo(userInfo);
        }catch (Exception e){
            return ResultGenerator.genFailResult("更新出错");
        }
        return  ResultGenerator.genSuccessResult("更新成功");
    }
    @PostMapping("/deleteUser")
    public Result delete(@ApiParam(value = "用户Id")Integer userId,
                         @ApiParam(value = "用户token",required = true)String token){
        String str=tokenUtil.checkToken(token);
        JSONObject jsonObject=JSONObject.fromObject(str);
        if (!jsonObject.optString("isSuper").equals("0")){
            return ResultGenerator.genFailResult("权限不足");
        }
        userService.deleteByUserId(userId);
        return ResultGenerator.genSuccessResult("ok");
    }
}