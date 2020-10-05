package com.springboot.test.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * @author 始皇
 */
@Slf4j
public class RestTemplateInterceptor  implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        log.info("enter interceptor...");
        return clientHttpRequestExecution.execute(request,body);
    }
}
