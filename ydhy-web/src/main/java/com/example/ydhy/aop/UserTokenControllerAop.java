package com.example.ydhy.aop;

import com.example.ydhy.Re.Result;
import com.example.ydhy.Re.ResultGenerator;
import com.example.ydhy.util.TokenUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class UserTokenControllerAop {
    @Autowired
    private TokenUtil tokenUtil;
    @Pointcut("@annotation(com.example.ydhy.aop.UserTokenAop)")
    public void userTokenPointCut(){
    }
    @Pointcut("@annotation(com.example.ydhy.aop.AdminTokenAop)")
    public void adminTokenPointCut(){
    }
    @Around("userTokenPointCut()")
    public Result around(ProceedingJoinPoint proceedingJoinPoint) throws  Throwable {
        String token=getRequest().getParameter("token");
        String str=tokenUtil.checkToken(token);
        if (str.equals("token无效")){
            return ResultGenerator.genFailResult(str);
        }
        return (Result) proceedingJoinPoint.proceed();
    }

    private HttpServletRequest getRequest() {
        ServletRequestAttributes sra=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return sra.getRequest();
    }
}
