package com.springboot.test.listener;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.test.model.PushInfo;
import com.springboot.test.websocket.WebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author zhoujian
 * @date 2019/6/5
 */
@Slf4j
@Component
public class RedisReceiver {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void onMessage(String message) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        ObjectMapper mapper = new ObjectMapper();
        PushInfo info = null;
        try {
            info = mapper.readValue(jackson2JsonRedisSerializer.deserialize(message.getBytes()).toString(), PushInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("收到的mq消息:" + info.toString());

        try {
            WebSocketHandler.send(mapper.writeValueAsString(info));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
