package com.springboot.test.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 始皇
 */
@Configuration
public class RestTemplateConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public RestTemplate restTemplate(OkHttpClient okHttpClient){
        RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory(okHttpClient));
        List<ClientHttpRequestInterceptor> list = new ArrayList<>();
        list.add(new RestTemplateInterceptor());
        restTemplate.setInterceptors(list);
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());
        return restTemplate;
    }

}
