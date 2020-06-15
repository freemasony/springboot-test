package com.springboot.test.scheduler;

import com.springboot.test.model.user.entity.SysJobTask;
import com.springboot.test.repository.user.SysJobTaskDao;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

public class SysJobRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SysJobRunner.class);

    @Autowired
    private SysJobTaskDao sysJobTaskDao;

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    @Override
    public void run(String... strings) throws Exception {
        // 初始加载数据库里状态为正常的定时任务
        List<SysJobTask> jobList = sysJobTaskDao.findByStatus(1);
        if (CollectionUtils.isNotEmpty(jobList)) {
            for (SysJobTask job : jobList) {
                SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), StringUtils.isNotBlank(job.getMethodParams()) ? job.getMethodParams().split(",") : null);
                cronTaskRegistrar.addCronTask(task, job.getCronExpres());
            }
            logger.info("定时任务已加载完毕...");
        }
    }
}
