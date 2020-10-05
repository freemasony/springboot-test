package com.springboot.test.controller;

import com.springboot.test.common.JsonResult;
import com.springboot.test.webserivce.WebServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoujian
 * @date 2020/6/12
 */
@Controller
@RequestMapping(value = "/ws")
public class WebServiceController {

    @Autowired
    private WebServiceClient webServiceClient;

    @RequestMapping(value = "/invoke", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult invoke(){
        JsonResult result=new JsonResult();
        String data=webServiceClient.invokeWebService();
        result.setCode("200");
        result.setMsg("成功");
        Map<String,Object> map=new HashMap<>();
        map.put("data",data);
        result.setData(map);
        return result;
    }
}
