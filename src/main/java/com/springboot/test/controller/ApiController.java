package com.springboot.test.controller;

import com.springboot.test.common.JsonRsult;
import com.springboot.test.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhoujian on 2018/9/5.
 */
@Controller
public class ApiController {

    private Logger logger= LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private MailService mailService;


    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String  get(HttpServletRequest httpServletRequest, HttpServletResponse response, Model model) throws IOException {
        model.addAttribute("code","00001");
        return "test";
    }


    @RequestMapping(value = "/sendmail/{sendType}",method = RequestMethod.GET)
    @ResponseBody
    public JsonRsult sendMail(HttpServletRequest request,HttpServletResponse response, @PathVariable(name = "sendType") int sendType){
        JsonRsult rsult=new JsonRsult();

        logger.info("***sendType:"+sendType);

        switch (sendType){
            case 1:
                mailService.sendSimpleMail();
                break;
            case 2:
                mailService.sendMimeMail();
                break;
            case 3:
                mailService.sendInlineMail();
                break;
            case 4:
                mailService.sendTemplateMail();
                break;
        }


        rsult.setCode("0000");
        rsult.setMsg("发送成功");
        return rsult;
    }
}
