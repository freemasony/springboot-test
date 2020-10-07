package com.springboot.test.controller;

import com.springboot.test.service.RestApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 始皇
 */
@RestController
@RequestMapping(value = "/restapi")
@Slf4j
public class RestApiController {

    @Autowired
    private RestApiService restApiService;

    @GetMapping("/test")
    public String test(String name,Integer age){
        log.info("name:{}|age:{}",name,age);
        return restApiService.test();
    }
}
