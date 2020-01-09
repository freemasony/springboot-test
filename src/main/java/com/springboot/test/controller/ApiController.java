package com.springboot.test.controller;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.springboot.test.common.JsonRsult;
import com.springboot.test.model.UserInfo;
import com.springboot.test.model.entity.Admin;
import com.springboot.test.model.vo.JsonInfo;
import com.springboot.test.push.dingding.DingtalkService;
import com.springboot.test.push.jgpush.JPushService;
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
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private JPushService jPushService;


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
    public JsonRsult listParams(@RequestBody UserInfo userInfo) {
        JsonRsult rsult = new JsonRsult();

        Map<String, Object> map = new HashMap<>();
        map.put("userInfo", userInfo);
        rsult.setCode("0000");
        rsult.setData(map);
        return rsult;
    }

    @RequestMapping(value = "/jackson",method = RequestMethod.GET)
    @ResponseBody
    public JsonRsult<JsonInfo> jackson(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String json="{\"aname\":\"周建\",\"aage\":30,\"asex\":\"男\"}";
        ObjectMapper mapper=new ObjectMapper();
        JsonInfo info=mapper.readValue(json, JsonInfo.class);
        JsonRsult rsult=new JsonRsult();
        rsult.setCode("0000");
        rsult.setMsg("发送成功");
        rsult.getData().put("info",info);
        return rsult;
    }


    @RequestMapping(value = "/image",method = RequestMethod.POST)
    @ResponseBody
    public JsonRsult image(HttpServletRequest request,HttpServletResponse response,String image){
        JsonRsult rsult=new JsonRsult();
        rsult.setCode("0000");
        rsult.setMsg(image);
        return rsult;
    }

    @RequestMapping(value = "/push",method = RequestMethod.GET)
    @ResponseBody
    public JsonRsult push() throws APIConnectionException, APIRequestException, InterruptedException {
        JsonRsult rsult=new JsonRsult();
        for(int i=1;i<=10;i++){
            jPushService.push();
            TimeUnit.SECONDS.sleep(1);
        }

        rsult.setCode("0000");
        rsult.setMsg("");
        return rsult;
    }

    @RequestMapping(value = "/dd",method = RequestMethod.GET)
    @ResponseBody
    public JsonRsult dd() throws Exception {
        JsonRsult rsult=new JsonRsult();
//        dingtalkService.getAuthScopes();
//        dingtalkService.getDepartmentList();
//        dingtalkService.getUserInfoByDepartMentId();
//        dingtalkService.send();
        rsult.setCode("0000");
        rsult.setMsg("");
        return rsult;
    }



}
