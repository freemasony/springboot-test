package com.springboot.test.service;

import com.springboot.test.controller.ApiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author user
 */
@Component
public class ThreadService {

    private Logger logger= LoggerFactory.getLogger(ApiController.class);


    @Async
    public void threadRun(int num) throws InterruptedException {
        logger.info("执行线程:"+Thread.currentThread().getName()+"--开始:"+num);
        Thread.sleep(3000);
        logger.info("执行线程:"+Thread.currentThread().getName()+"--结束:"+num);
    }
}
