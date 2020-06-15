package com.springboot.test.scheduler;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhoujian
 * @date 2020/2/21
 */
@Component
public class CronTaskRegistrar implements DisposableBean {
    private final Map<Runnable, ScheduledFutureTask> scheduledTasks = new ConcurrentHashMap<>(16);

    @Autowired
    private TaskScheduler taskScheduler;

    public TaskScheduler getTaskScheduler() {
        return taskScheduler;
    }

    public void addCronTask(Runnable task, String cronExpression) {
        addCronTask(new CronTask(task, cronExpression));
    }


    public void addCronTask(CronTask cronTask) {
        if (cronTask != null) {
            Runnable task = cronTask.getRunnable();
            if (this.scheduledTasks.containsKey(task)) {
                removeCronTask(task);
            }

            this.scheduledTasks.put(task, scheduleCronTask(cronTask));
        }
    }


    public void removeCronTask(Runnable task) {
        ScheduledFutureTask scheduledTask = this.scheduledTasks.remove(task);
        if (scheduledTask != null){
            scheduledTask.cancel();
        }
    }

    public ScheduledFutureTask scheduleCronTask(CronTask cronTask) {
        ScheduledFutureTask scheduledTask = new ScheduledFutureTask();
        scheduledTask.future = this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());

        return scheduledTask;
    }

    @Override
    public void destroy() throws Exception {
        for (ScheduledFutureTask task : this.scheduledTasks.values()) {
            task.cancel();
        }
        this.scheduledTasks.clear();
    }
}
