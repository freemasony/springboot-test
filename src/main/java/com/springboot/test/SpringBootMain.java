package com.springboot.test;

import com.xxl.job.core.executor.XxlJobExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * Created by Administrator on 2017/5/31.
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
public class SpringBootMain extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMain.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder application) {
        return application.sources(SpringBootMain.class);
    }



//    @Bean(initMethod = "start",destroyMethod = "destroy")
    public XxlJobExecutor xxlJobExecutor(@Value("${xxl.job.address}") String address,
                                         @Value("${xxl.job.logpath}")String logpath,
                                         @Value("${xxl.job.executor.port}") int port){
        XxlJobExecutor xxlJobExecutor=new XxlJobExecutor();
        xxlJobExecutor.setPort(port);
        xxlJobExecutor.setAppName("xxl-job-springboot");
        xxlJobExecutor.setAdminAddresses(address);
        xxlJobExecutor.setLogPath(logpath);
        return xxlJobExecutor;
    }


    /**
     * 文件上传临时路径
     */
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String location = System.getProperty("user.dir") + "/data/tmp";
        File tmpFile = new File(location);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        factory.setLocation(location);
        return factory.createMultipartConfig();
    }

}