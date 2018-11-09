package com.example.ydhy.controller;


import com.example.ydhy.dao.UserMapper;
import com.example.ydhy.entity.User;
import com.example.ydhy.util.ResponseUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONArray;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ResponseUtil responseUtil;
    @PostMapping(value = "/user/login")
    public String login(
            @ApiParam(value = "用户名", required = true) @RequestParam(value = "loginName") String loginName,
            @ApiParam(value = "密码", required = true) @RequestParam(value = "password") String password, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
//        responseUtil.setHeader(httpServletRequest,httpServletResponse);
        httpServletResponse.setHeader("Set-cookie","DSDASDASDAS");
        return "登陆成功";
    }
    @GetMapping("getAll")
    public List<User> getAll(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        Example e=new Example(User.class);
        e.createCriteria().getAllCriteria();
        List<User> user=userMapper.selectByExample(e);
        return user;
    }
    @PostMapping("select")
    public List<User> select(@Param("name")String name, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        System.out.println(httpServletRequest.getSession().getId());
        Example e=new Example(User.class);
        e.createCriteria().andEqualTo("name",name);
        List<User> user=userMapper.selectByExample(e);
        return user;
    }

    @GetMapping("mady")
    public JSONArray Mady(){
        Example e=new Example(User.class);
        e.createCriteria().andEqualTo("id","1");
        List<User> user=userMapper.selectByExample(e);
        return JSONArray.fromObject(user);
    }
    @GetMapping("madd")
    public List<User> Madd(){
        Example e=new Example(User.class);
        e.createCriteria().andEqualTo("id","1");
        List<User> user=userMapper.selectByExample(e);
        return user;
    }
    @PostMapping("mady")
    public String Mady2(){
        Example e=new Example(User.class);
        e.createCriteria().andEqualTo("id","1");
        List<User> l=userMapper.selectByExample(e);
        return l.get(0).toString();
    }


    @GetMapping("/itemsPage")
    public List<User> itemsPage(int page,int size){
        Page<User> pageInfo = PageHelper.startPage(page, size);
        List<User> users = userMapper.selectAll();
        return pageInfo;
    }
}