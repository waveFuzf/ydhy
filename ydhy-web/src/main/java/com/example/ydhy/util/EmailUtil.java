package com.example.ydhy.util;

import com.example.ydhy.entity.Request;
import com.example.ydhy.service.BorderRoomService;
import com.example.ydhy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;

@Component
public class EmailUtil{
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private JavaMailSenderImpl javaMailSenderImpl;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private UserService userService;
    @Autowired
    private BorderRoomService borderRoomService;

    private static final Logger logger =LoggerFactory.getLogger(EmailUtil.class);
    public void sendEmails(String[] users, Request re, boolean type) throws MessagingException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd   HH:mm");
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
        helper.setFrom("904560968@qq.com");
        helper.setTo(users);
        helper.setSubject(type?"[会议通知] "+re.getTheme():"[会议取消通知] "+re.getTheme());
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("<div>发起人: "+userService.getByUserId(re.getUserId()).getRealName()+"</div>");
        stringBuffer.append("<div>会议地点: "+borderRoomService.getById(re.getRoomId()).getRoomName()+"</div>");
        stringBuffer.append("<div>会议时间: "+format.format(re.getBeginTime())+"至"+format.format(re.getEndTime())+"</div>");
        helper.setText(stringBuffer.toString(),true);
        taskExecutor.execute(new SendEmailThread(mimeMessage));
    }
    class SendEmailThread implements Runnable{
        private MimeMessage mimeMessage;

        public SendEmailThread(MimeMessage mimeMessage) {
            this.mimeMessage=mimeMessage;
        }

        @Override
        public void run() {
            javaMailSenderImpl.send(mimeMessage);
        }
    }
//    private JavaMailSender javaMailSender;
//    private String email;
//    public EmailUtil(JavaMailSender javaMailSender,String email){
//        this.javaMailSender=javaMailSender;
//        this.email=email;
//    }
//    private static final Logger logger =LoggerFactory.getLogger(EmailUtil.class);
//    public void sendSimpleMail(){
//        MimeMessage mimeMessage=null;
//        try{
//            mimeMessage=javaMailSender.createMimeMessage();
//            MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
//            helper.setFrom("904560968@qq.com");
//            helper.setTo(email);
//            helper.setSubject("标题：李思鹏你是猪么？");
//            helper.setText("嗯 是的");
////            FileSystemResource fileSystemResource=new FileSystemResource();
//            javaMailSender.send(mimeMessage);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void run() {
//        logger.info("=====开始发送邮件=====");
//        sendSimpleMail();
//        logger.info("=====邮件发送成功=====");
//    }
}
