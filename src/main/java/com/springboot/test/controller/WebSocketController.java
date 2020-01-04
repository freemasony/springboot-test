package com.springboot.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springboot.test.listener.RedisReceiver;
import com.springboot.test.service.RedisService;
import com.springboot.test.websocket.WebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhoujian
 * @date 2019/6/3
 */
@Controller
@RequestMapping("/msg")
public class WebSocketController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    @Autowired
    private RedisService redisService;

    @RequestMapping("/push")
    @ResponseBody
    public void push(String message){
        WebSocketHandler.send(message);
    }

    @RequestMapping("/publish")
    @ResponseBody
    public void publish(String message) throws JsonProcessingException {
        redisService.publishMessge(message);
    }

    @RequestMapping("/put")
    @ResponseBody
    public void put(String message){
        redisService.put(message);
    }

    @RequestMapping("/get")
    @ResponseBody
    public Object  get(){
        return redisService.get();
    }

}
