package com.example.ydhy.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ResponseUtil {

    public void setHeader(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        if (!httpServletRequest.getSession().getId().equals(httpServletRequest.getRequestedSessionId())) {
            httpServletResponse.setHeader("Set-Cookie", "JSESSIONID=" + httpServletRequest.getSession().getId());
        }
    }
}
