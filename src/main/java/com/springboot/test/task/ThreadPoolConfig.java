package com.springboot.test.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author user
 */
@Configuration
public class ThreadPoolConfig {
    private static final int COREPOOLSIZE = 3;

    private static final int MAXPOOLSIZE = 5;

    private static final int QUEUECAPACITY = 8;

    private static final int KEEPALIVESECONDS = 60*60*12;

    @Bean
    public AsyncTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("async-thread-");
        executor.setCorePoolSize(COREPOOLSIZE);
        executor.setMaxPoolSize(MAXPOOLSIZE);
        executor.setQueueCapacity(QUEUECAPACITY);
        executor.setKeepAliveSeconds(KEEPALIVESECONDS);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        executor.initialize();
        return executor;
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        // 定时任务执行线程池核心线程数
        taskScheduler.setPoolSize(4);
        taskScheduler.setRemoveOnCancelPolicy(true);
        taskScheduler.setThreadNamePrefix("scheduler-thread-");
        return taskScheduler;
    }
}
