package com.example.ydhy.controller;

import com.example.ydhy.util.EmailUtil;
import com.example.ydhy.util.ImageWrite;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.example.ydhy.util.ImageWrite.toBufferedImage;

@CrossOrigin
@RestController
public class TestController {
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private JavaMailSenderImpl javaMailSenderImpl;

    private static final Logger logger =LoggerFactory.getLogger(TestController.class);
    //多线程发送
    @GetMapping("/sendEmail")
    public String sendEmail(){
        taskExecutor.execute(new EmailUtil(javaMailSender,"906638848@qq.com"));
        taskExecutor.execute(new EmailUtil(javaMailSender,"zhibi513155315@163.com"));
        return "发送成功!";
    }
    @GetMapping("/sendEmails")
    public String sendEmails() throws MessagingException {
        String users[]={"zhibi513155315@163.com","zhibi513155315@163.com",
                "zhibi513155315@163.com","zhibi513155315@163.com","zhibi513155315@163.com",
                "zhibi513155315@163.com","zhibi513155315@163.com","zhibi513155315@163.com",
                "zhibi513155315@163.com","zhibi513155315@163.com","zhibi513155315@163.com"};
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
        helper.setFrom("904560968@qq.com");
        helper.setTo(users);
        helper.setSubject("标题：马丁扬你是猪么？");
        helper.setText("嗯 是的");
//            FileSystemResource fileSystemResource=new FileSystemResource();
        logger.info("======");
        javaMailSenderImpl.send(mimeMessage);
        logger.info("======");
        return "群发成功";
    }
    @GetMapping("getQRcode")
    public void getORcode(HttpServletResponse response) throws WriterException, IOException {
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN,0);
        BitMatrix bitMatrix = new MultiFormatWriter()
                .encode("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxeb42c0983b8b3234&" +
                                "http%3A%2F%2F192.168.22.223%3A8080%2Fvxloading&response_type=code&scope=snsapi_userinfo&" +
                                "&connect_redirect=1#wechat_redirect",
                BarcodeFormat.QR_CODE, 400, 400,hints);
        ImageWrite.writeToStream(bitMatrix, "png", response.getOutputStream());
    }
    @GetMapping("vxloading")
    public String vxLoading(){
        return "微信登陆成功！";
    }

}
