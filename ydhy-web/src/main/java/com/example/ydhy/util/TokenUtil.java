package com.example.ydhy.util;

import com.example.ydhy.entity.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class TokenUtil {
    @Autowired
    private RedisTemplate redisTemplate;
    public Token createToken (String username){
        String tokenId = UUID.randomUUID().toString().replace("-","");
        Token token = new Token(username,tokenId);
        redisTemplate.boundValueOps(username).set(tokenId,150,TimeUnit.SECONDS);
        return token;
    }
    public Token getToken (String authentication) {
        if (authentication == null || authentication.length () == 0) {
            return null;
        }
        String [] param = authentication.split ("_");
        if (param.length != 2) {
            return null;
        }
        // 使用 userId 和源 token 简单拼接成的 token，可以增加加密措施
        String username = param [0];
        String token = param [1];
        return new Token (username, token);
    }

    public boolean checkToken (Token model) {
        if (model == null) {
            return false;
        }
        String token = (String) redisTemplate.boundValueOps (model.getUsername()).get ();
        if (token == null || !token.equals (model.getToken ())) {
            return false;
        }
        redisTemplate.boundValueOps (model.getUsername()).expire (150, TimeUnit.SECONDS);
        return true;
    }

    public void deleteToken (String userId) {
        redisTemplate.delete (userId);
    }
}
