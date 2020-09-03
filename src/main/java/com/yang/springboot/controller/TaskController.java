package com.yang.springboot.controller;


import com.yang.springboot.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Autowired
    private AsyncService asyncService;
    @RequestMapping("async")
    public String async(){
        asyncService.thread();
        return "ok";
    }
    @RequestMapping("scheduled")
    @Scheduled(cron = "0-4 * * * * MON-FRI")
    public void scheduled(){
        System.out.println("11111");
    }
    @RequestMapping("mail")
    public String mail() throws MessagingException {
        MimeMessage mimeMailMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMailMessage,true);
        mimeMessageHelper.setFrom("2975165963@qq.com");
        mimeMessageHelper.setTo("1512263934@qq.com");
        mimeMessageHelper.setSubject("测试");
        mimeMessageHelper.setText("<b style:'color:red'>今晚搞宋子文</b>",true);
        mimeMessageHelper.addAttachment("作战计划", new File("D:\\steam\\steamapps\\workshop\\content\\431960\\1175327876\\preview.jpg"));
        javaMailSender.send(mimeMailMessage);
        return "success";
    }

}
