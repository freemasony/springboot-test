package com.springboot.test.controller;

import com.springboot.test.common.JsonResult;
import com.springboot.test.common.JsonSerializeUtil;
import com.springboot.test.model.user.entity.User;
import com.springboot.test.service.RestServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 始皇
 */
@RestController
@Slf4j
@RequestMapping(value = "/rest/server")
public class RestServerController {

    @Autowired
    private RestServerService restServerService;

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public String test(HttpServletRequest httpServletRequest ,@RequestParam String name, @RequestParam Integer age){
        JsonResult result=new JsonResult();
        Map<String,User> map=new HashMap<>();
        User user = new User();
        user.setName(name);
        user.setAge(age);
        map.put("data",user);
        result.setData(map);
        result.setMsg("success");
        result.setCode("0000");
        return JsonSerializeUtil.objectToJson(result);
    }
}
