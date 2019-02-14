package com.springboot.test.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author user
 */
@Component
public class MailService {


    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender mailSender;


    @Value("${mail.fromAddress}")
    private String fromAddress;

    @Value("${mail.toAddress}")
    private String toAddress;

    /**
     * 发送简单邮件
     * @return
     */
    public String sendSimpleMail(){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(toAddress);
        message.setSubject("这是封邮件");
        message.setText("我说这是封邮件你信不?!");
        mailSender.send(message);
        return "ok";
    }


    /**
     * 发送附件
     * @return
     * @throws MessagingException
     */
    public String sendMimeMail() {
        try {
            MimeMessage message=mailSender.createMimeMessage();
            MimeMessageHelper messageHelper=new MimeMessageHelper(message,true);
            messageHelper.setFrom(fromAddress);
            messageHelper.setTo(toAddress);
            messageHelper.setSubject("这是封有附件的邮件");
            messageHelper.setText("这是封有附件的邮件你知道不?!");
            FileSystemResource resource=new FileSystemResource(new File("D:\\GQGETfiles\\huakuai\\2.png"));
            messageHelper.addAttachment("附件-001.png",resource);
            mailSender.send(message);

        }catch (Exception e){
            e.printStackTrace();
        }

        return "ok";
    }


    /**
     * 发送带静态资源的邮件
     * @return
     */
    public String sendInlineMail(){
        try {
            MimeMessage mimeMessage=mailSender.createMimeMessage();
            MimeMessageHelper messageHelper=new MimeMessageHelper(mimeMessage,true);
            messageHelper.setFrom(fromAddress);
            messageHelper.setTo(toAddress);
            messageHelper.setSubject("这是封有静态资源图的邮件");
            messageHelper.setText("<html><body><img src=\"cid:image\" ></body></html>", true);
            FileSystemResource resource=new FileSystemResource(new File("D:\\GQGETfiles\\huakuai\\2.png"));
            messageHelper.addInline("image",resource);
            mailSender.send(mimeMessage);

        }catch (Exception e){
            e.printStackTrace();
        }

        return "ok";
    }


    /**
     * 发送模板邮件
     * @return
     */
    public String sendTemplateMail(){
        try {
            Context context=new Context();
            context.setVariable("username","周建");
            String emailContent=templateEngine.process("emailtemplate",context);

            MimeMessage message=mailSender.createMimeMessage();
            MimeMessageHelper messageHelper=new MimeMessageHelper(message,true);
            messageHelper.setFrom(fromAddress);
            messageHelper.setTo(toAddress);
            messageHelper.setSubject("这是封模板邮件");
            messageHelper.setText(emailContent,true);
            mailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }


        return "ok";
    }
}
