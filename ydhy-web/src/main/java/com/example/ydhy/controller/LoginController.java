package com.example.ydhy.controller;

import com.example.ydhy.Re.Result;
import com.example.ydhy.Re.ResultGenerator;
import com.example.ydhy.entity.Token;
import com.example.ydhy.entity.User;
import com.example.ydhy.service.UserService;
import com.example.ydhy.util.TokenUtil;
import com.example.ydhy.util.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
public class LoginController {
    @Autowired
    private RedisTemplate stringredisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private UserService userService;

    @PostMapping("/sign")
    public Result Sign(@ApiParam(value = "用户名", required = true) @RequestParam(value = "loginName") String username,
                       @ApiParam(value = "密码", required = true) @RequestParam(value = "password") String password){
        if (userService.getByUsername(username)!=null){
            userService.save(new User(username,
                    new SimpleHash("md5", password, ByteSource.Util.bytes(""),
                            2).toHex()));
            return ResultGenerator.genSuccessResult("注册成功.");
        }
        return ResultGenerator.genFailResult("注册失败.");
    }

    @PostMapping("login")
    public Result doLogin(@ApiParam(value = "用户名", required = true) @RequestParam(value = "loginName") String username,
                          @ApiParam(value = "密码", required = true) @RequestParam(value = "password") String password, HttpServletResponse httpServletResponse) {
        Subject subject = SecurityUtils.getSubject();
        password = new SimpleHash("md5", password, ByteSource.Util.bytes(""), 2).toHex();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);//通过MyshiroRealm的doGetAuthenticationInfo()方法来验证是否正确
        } catch (AuthenticationException e) {
            token.clear();
            return ResultGenerator.genFailResult("登录失败，用户名或密码错误！");
        }
        User user=userService.getByUsername(username);
        userService.changeStatusByname(username);
        JSONObject userSession = JSONObject.fromObject(user);
        String redistoken=tokenUtil.createToken(userSession);
        httpServletResponse.setHeader("Set-cookie",redistoken);
        return ResultGenerator.genSuccessResult("登录成功");
    }
    @PostMapping("logout")
    public String logOut(@ApiParam(value = "用户token", required = true) @RequestParam(value = "token") String token,
                         @ApiParam(value = "用户名", required = true) @RequestParam(value = "username") String username){
        tokenUtil.deleteToken(token);
        userService.changeStatusByname(username);
        return "退出成功！";
    }
    @PostMapping("checktoken")
    public String checkToken(@ApiParam(value = "用户token", required = true) @RequestParam(value = "token") String token){
        String checkuser=tokenUtil.checkToken(token);
        return checkuser;
    }
}