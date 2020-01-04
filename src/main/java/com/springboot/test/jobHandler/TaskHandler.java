package com.springboot.test.jobHandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhoujian
 * @date 2019/7/31
 */
@JobHandler(value = "taskHandler")
@Service
@Slf4j
public class TaskHandler extends IJobHandler {
    @Override
    public ReturnT<String> execute(String s) throws Exception {
        XxlJobLogger.log("自动跑批任务开始...");
        XxlJobLogger.log("自动跑批任务完成...");
        return ReturnT.SUCCESS;
    }
}
