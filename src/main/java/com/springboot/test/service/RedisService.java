package com.springboot.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.test.model.PushInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhoujian
 * @date 2019/6/5
 */
@Component
public class RedisService {

    private static String key="push:message:websocket";

    private static AtomicInteger count=new AtomicInteger(0);

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    public void put(Object value){
        redisTemplate.opsForValue().set(key,value);
    }

    public Object get(){
        return redisTemplate.opsForValue().get(key);
    }

    public void publishMessge(String message) throws JsonProcessingException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        PushInfo info=new PushInfo(message,count.incrementAndGet(),sdf.format(new Date()));
        ObjectMapper objectMapper=new ObjectMapper();

        redisTemplate.convertAndSend("websocketTopic",objectMapper.writeValueAsString(info));
    }
}
