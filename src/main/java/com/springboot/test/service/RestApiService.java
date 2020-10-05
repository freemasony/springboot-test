package com.springboot.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestApiService {
    @Autowired
    private RestTemplate restTemplate;

    public String test(){
        String result="";
        ResponseEntity<String> results= restTemplate.exchange("http://localhost:9010/admin/admin", HttpMethod.GET,null,String.class);
        result=results.getBody();
        return result;
    }
}
