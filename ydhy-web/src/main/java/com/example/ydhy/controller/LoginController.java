package com.example.ydhy.controller;

import com.example.ydhy.Re.Result;
import com.example.ydhy.Re.ResultGenerator;
import com.example.ydhy.entity.Token;
import com.example.ydhy.util.TokenUtil;
import com.example.ydhy.util.TokenUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@CrossOrigin
@RestController
public class LoginController {
    @Autowired
    private StringRedisTemplate stringredisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping("/doLogin")
    public Result doLogin(String username, String password, HttpServletResponse httpServletResponse) {
        Subject subject = SecurityUtils.getSubject();
        password = new SimpleHash("md5", password, ByteSource.Util.bytes(""), 2).toHex();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);//通过MyshiroRealm的doGetAuthenticationInfo()方法来验证是否正确
        } catch (AuthenticationException e) {
            token.clear();
            return ResultGenerator.genFailResult("登录失败，用户名或密码错误！");
        }
        Token redistoken=tokenUtil.createToken(username);
        httpServletResponse.setHeader("Set-cookie",redistoken.getToken());
        return ResultGenerator.genSuccessResult("登录成功");
    }
    @RequestMapping("logout")
    public String logOut(String username){
        tokenUtil.deleteToken(username);
        return "退出成功！";
    }
    @RequestMapping("checktoken")
    public String checkToken(String username,String token){
        boolean checkuser=tokenUtil.checkToken(new Token(username,token));
        return String.valueOf(checkuser);

    }
    @RequestMapping("/emmm")
    public String index(ModelMap map) {
        map.addAttribute("host", "http://blog.didispace.com");
        return "index";
    }
    @GetMapping("/redis")
    public void setValue() {
        stringredisTemplate.opsForValue().set("fuzf","mady");
        redisTemplate.opsForValue().set("mady","fuzf");

//        Map<String ,String> maps=new HashMap<String ,String>();
//        maps.put("user1","mady");
//        maps.put("user2","fuzf");
//        maps.put("user3","momh");
//        stringredisTemplate.opsForValue().multiSet(maps);
//        setredisTemplate(redisTemplate);
//        System.out.print(redisTemplate.opsForList().range("user",0,-1));
//        Collection c=new ArrayList<Object>();
//        String a[]=new String []{"mady","fuzf","momh"};
//        c.addAll(Arrays.asList(a));
//        redisTemplate.opsForList().leftPushAll("mady",a);
//        System.out.println(redisTemplate.opsForList().range("mady",0,1));
//        Map<String,String > user=new HashMap<String,String>();
//        user.put("id","1150299351");
//        user.put("name","chjq");
//        user.put("school","zust");
//        user.put("age","22");
//        stringredisTemplate.opsForHash().putAll("user2",user);
//        redisTemplate.opsForHash().putAll("user3",user);
//        System.out.print(stringredisTemplate.opsForHash().values("user2"));
//        System.out.print(stringredisTemplate.opsForHash().values("user3"));
//        System.out.print(redisTemplate.opsForHash().values("user2"));
//        System.out.print(redisTemplate.opsForHash().values("user3"));
//        stringredisTemplate.opsForSet().add("friend","goyc");
//        System.out.println("zgjh：****"+stringredisTemplate.opsForSet().isMember("friend","zgjh"));
//        System.out.println("mady：****"+stringredisTemplate.opsForSet().isMember("friend","mady"));
    }
}