package com.springboot.test.service;

import com.springboot.test.common.JsonResult;
import com.springboot.test.model.entity.SysJobTask;
import com.springboot.test.repository.SysJobTaskDao;
import com.springboot.test.scheduler.CronTaskRegistrar;
import com.springboot.test.scheduler.SchedulingRunnable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhoujian
 * @date 2020/2/21
 */
@Component
public class SysJobTaskService {

//    https://mp.weixin.qq.com/s/wNM3tXtMUr5OC4N-JopvGg

    @Autowired
    private SysJobTaskDao sysJobTaskDao;

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    public List<SysJobTask> list(Integer status) {
        return sysJobTaskDao.findByStatus(status);
    }

    public SysJobTask save(SysJobTask task) {
        return sysJobTaskDao.save(task);
    }


    public Boolean addSysJob(SysJobTask task) {
        SysJobTask success = save(task);
        if (task.getStatus() == 1) {
            SchedulingRunnable schedulingRunnable = new SchedulingRunnable(task.getBeanName(), task.getMethodName(),
                    StringUtils.isNotBlank(task.getMethodParams()) ? task.getMethodParams().split(",") : null);
            cronTaskRegistrar.addCronTask(schedulingRunnable, task.getCronExpres());
        }
        return Boolean.TRUE;
    }

    public Boolean addTask(String beanName, String methodName, String cronExpression, String params) {
        SchedulingRunnable task = new SchedulingRunnable(beanName, methodName, StringUtils.isEmpty(params)?null:params.split(","));
        cronTaskRegistrar.addCronTask(task, cronExpression);
        return Boolean.TRUE;
    }
}
