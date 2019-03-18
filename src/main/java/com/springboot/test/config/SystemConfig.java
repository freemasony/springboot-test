package com.springboot.test.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig implements InitializingBean {

    private Logger logger= LoggerFactory.getLogger(SystemConfig.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("*****************我是配置类");
    }
}
