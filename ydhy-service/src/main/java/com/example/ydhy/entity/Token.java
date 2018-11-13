package com.example.ydhy.entity;

import lombok.Data;

@Data
public class Token {
    private User user;
    private String token;

    public Token(User user, String tokenId) {
        this.user=user;
        this.token=tokenId;
    }
}
