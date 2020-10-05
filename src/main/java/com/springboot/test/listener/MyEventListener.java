package com.springboot.test.listener;

import com.springboot.test.common.JsonSerializeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author zhoujian
 * @date 2020/4/28
 */
@Component
@Slf4j
public class MyEventListener {

    @Async
    @EventListener
    public void doPost(MyEvent event){
        log.info("监听事件event:"+JsonSerializeUtil.objectToJson(event));
    }
}
