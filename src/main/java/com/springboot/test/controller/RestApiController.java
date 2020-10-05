package com.springboot.test.controller;

import com.springboot.test.service.RestApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 始皇
 */
@RestController
@RequestMapping(value = "/restapi")
public class RestApiController {

    @Autowired
    private RestApiService restApiService;

    @GetMapping("/test")
    public String test(){
        return restApiService.test();
    }
}
