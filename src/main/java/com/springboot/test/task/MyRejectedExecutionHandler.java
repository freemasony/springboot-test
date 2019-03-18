package com.springboot.test.task;

import com.springboot.test.controller.ApiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author user
 */
public class MyRejectedExecutionHandler implements RejectedExecutionHandler {

    private Logger logger= LoggerFactory.getLogger(ApiController.class);

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            LinkedBlockingQueue<Runnable> queue=new LinkedBlockingQueue<>();
            queue.add(r);
            logger.info("##############");
            executor.execute(r);
    }
}
