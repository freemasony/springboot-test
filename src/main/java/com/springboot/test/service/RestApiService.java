package com.springboot.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class RestApiService {
    @Autowired
    private RestTemplate restTemplate;

    public String test(){
        String result="";
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("name", "zhoujian");
        params.add("age", "30");
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(params,headers);

        ResponseEntity<String> results = restTemplate.exchange("http://localhost:9010/rest/server/test",HttpMethod.POST,httpEntity,String.class);
        String t=restTemplate.postForObject("http://localhost:9010/rest/server/test",httpEntity,String.class);
        return t;
    }
}
