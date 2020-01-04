package com.springboot.test.controller;

import com.google.common.collect.Maps;
import com.springboot.test.common.JsonRsult;
import com.springboot.test.model.UserInfo;
import com.springboot.test.model.entity.Admin;
import com.springboot.test.service.AdminService;
import com.springboot.test.service.MailService;
import com.springboot.test.service.ThreadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoujian on 2018/9/5.
 */
@Controller
public class ApiController {

    private Logger logger= LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private MailService mailService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ThreadService threadService;


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



    @RequestMapping(value = "/sqlinjection",method = RequestMethod.GET)
    public String  sqlinjection(HttpServletRequest httpServletRequest, HttpServletResponse response, Model model) throws IOException {
        model.addAttribute("admin",new Admin());
        return "sqlinjection";
    }

    @RequestMapping(value = "/sqlinjection",method = RequestMethod.POST)
    @ResponseBody
    public JsonRsult sqlInjection(@ModelAttribute(name = "admin")Admin admin){
        JsonRsult rsult=new JsonRsult();

        logger.info("参数:username{}|password:{}",admin.getUserName(),admin.getPassword());

        List<Admin> admins=adminService.query(admin.getUserName(),admin.getPassword());

        Map data= Maps.newHashMap();
        data.put("userinfo",admins);
        rsult.setData(data);
        rsult.setCode("0000");
        return rsult;
    }


    @RequestMapping(value = "/threadtest",method = RequestMethod.GET)
    @ResponseBody
    public JsonRsult threadtest(HttpServletRequest request,HttpServletResponse response){
        JsonRsult rsult=new JsonRsult();


        for (int i = 0; i < 16; i++) {
            try {
                threadService.threadRun(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        rsult.setCode("0000");
        rsult.setMsg("发送成功");
        return rsult;
    }

    @RequestMapping(value = "/jihe",method = RequestMethod.POST)
    @ResponseBody
    public JsonRsult listParams(@RequestBody UserInfo userInfo){
        JsonRsult rsult=new JsonRsult();

        Map<String,Object> map=new HashMap<>();
        map.put("userInfo",userInfo);
        rsult.setCode("0000");
        rsult.setData(map);
        return rsult;
    }
}
