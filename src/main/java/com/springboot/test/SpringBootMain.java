package com.springboot.test;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import com.springboot.test.websocket.NettyServer;
import com.xxl.job.core.executor.XxlJobExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.ResourceUtils;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.io.FileNotFoundException;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

/**
 * Created by Administrator on 2017/5/31.
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
@EnableAsync
public class SpringBootMain extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBootMain.class, args);
        new NettyServer(23456).start();
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
    MultipartConfigElement multipartConfigElement() throws FileNotFoundException {
        MultipartConfigFactory factory = new MultipartConfigFactory();

        String location = System.getProperty("user.dir") + "src/main/resources/excel";
        File tmpFile = new File(location);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        factory.setLocation(location);
        return factory.createMultipartConfig();
    }



    @Bean
    JPushClient jPushClient(){
        ClientConfig config = ClientConfig.getInstance();
        config.setPushHostName("https://bjapi.jpush.cn");
        config.setApnsProduction(true);
        JPushClient jPushClient=new JPushClient("ada966c60f603678cd47cca1","d870ee3c0d0fa8304f64eeca",null,config);
        return jPushClient;
    }

}
