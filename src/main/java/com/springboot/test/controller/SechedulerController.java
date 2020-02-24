package com.springboot.test.controller;

import com.springboot.test.common.JsonResult;
import com.springboot.test.service.SysJobTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhoujian
 * @date 2020/2/21
 */
@Controller
@RequestMapping(value = "/task")
public class SechedulerController {

    private Logger logger = LoggerFactory.getLogger(SechedulerController.class);

    @Autowired
    private SysJobTaskService sysJobTaskService;

    @RequestMapping(value = "/addTask", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult addTask(String beanName, String methodName, String cronExpression, String params){
        JsonResult result=new JsonResult();
        sysJobTaskService.addTask(beanName,methodName,cronExpression,params);
        result.setCode("200");
        result.setMsg("成功");
        return result;
    }

}
