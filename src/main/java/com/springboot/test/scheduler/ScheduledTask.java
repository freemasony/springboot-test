package com.springboot.test.scheduler;

import java.util.concurrent.ScheduledFuture;

/**
 * @author zhoujian
 * @date 2020/2/21
 */
public final class ScheduledTask {
    volatile ScheduledFuture future;

    public void cancel(){
        ScheduledFuture future=this.future;
        if(future!=null){
            future.cancel(true);
        }
    }
}
