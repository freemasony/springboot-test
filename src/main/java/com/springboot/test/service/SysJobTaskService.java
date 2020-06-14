package com.springboot.test.service;

import com.springboot.test.common.JsonResult;
import com.springboot.test.model.entity.SysJobTask;
import com.springboot.test.repository.SysJobTaskDao;
import com.springboot.test.scheduler.CronTaskRegistrar;
import com.springboot.test.scheduler.SchedulingRunnable;
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

    public List<SysJobTask> list(Integer status){
        return sysJobTaskDao.findByStatus(status);
    }

    public SysJobTask save(SysJobTask task){
        return sysJobTaskDao.save(task);
    }


    public Boolean addSysJob(SysJobTask task){
        task = save(task);
        if (task.getStatus() == 1) {
            SchedulingRunnable schedulingRunnable = new SchedulingRunnable(task.getBeanName(), task.getMethodName(), task.getMethodParams());
            cronTaskRegistrar.addCronTask(schedulingRunnable, task.getCronExpres());
        }
        return Boolean.TRUE;
    }

    public Boolean updateSysJob(SysJobTask task){
        task = sysJobTaskDao.save(task);
            //先移除再添加
            if (task.getStatus() == 1) {
                SchedulingRunnable schedulingRunnable = new SchedulingRunnable(task.getBeanName(), task.getMethodName(), task.getMethodParams());
                cronTaskRegistrar.removeCronTask(schedulingRunnable);
            }

            if (task.getStatus() == 1) {
                SchedulingRunnable schedulingRunnable = new SchedulingRunnable(task.getBeanName(), task.getMethodName(), task.getMethodParams());
                cronTaskRegistrar.addCronTask(schedulingRunnable, task.getCronExpres());
            }
        return Boolean.TRUE;
    }

    public Boolean deleteSysJob(Long taskId){
        SysJobTask task= sysJobTaskDao.findOne(taskId);
        sysJobTaskDao.delete(taskId);
        if (task.getStatus() == 1) {
            SchedulingRunnable schedulingRunnable = new SchedulingRunnable(task.getBeanName(), task.getMethodName(), task.getMethodParams());
            cronTaskRegistrar.removeCronTask(schedulingRunnable);
        }
        return Boolean.TRUE;
    }

    public Boolean startOrStop(Long taskId){
        SysJobTask task= sysJobTaskDao.findOne(taskId);
        if (task.getStatus() == 1) {
            SchedulingRunnable schedulingRunnable = new SchedulingRunnable(task.getBeanName(), task.getMethodName(), task.getMethodParams());
            cronTaskRegistrar.addCronTask(schedulingRunnable, task.getCronExpres());
        } else {
            SchedulingRunnable schedulingRunnable = new SchedulingRunnable(task.getBeanName(), task.getMethodName(), task.getMethodParams());
            cronTaskRegistrar.removeCronTask(schedulingRunnable);
        }
        return Boolean.TRUE;
    }
}
