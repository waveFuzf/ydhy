package com.example.ydhy.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;


import javax.mail.internet.MimeMessage;
import java.io.InputStream;

public class EmailUtil implements Runnable{

    private JavaMailSender javaMailSender;

    private String email;

    public EmailUtil(JavaMailSender javaMailSender,String email){
        this.javaMailSender=javaMailSender;
        this.email=email;
    }

    private static final Logger logger =LoggerFactory.getLogger(EmailUtil.class);

    public void sendSimpleMail(){
        MimeMessage mimeMessage=null;
        try{
            mimeMessage=javaMailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
            helper.setFrom("904560968@qq.com");
            helper.setTo(email);
            helper.setSubject("标题：李思鹏你是猪么？");
            helper.setText("嗯 是的");
//            FileSystemResource fileSystemResource=new FileSystemResource();
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        logger.info("=====开始发送邮件=====");
        sendSimpleMail();
        logger.info("=====邮件发送成功=====");
    }
}
