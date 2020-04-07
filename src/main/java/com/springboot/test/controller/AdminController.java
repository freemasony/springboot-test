package com.springboot.test.controller;

import com.springboot.test.common.JsonResult;
import com.springboot.test.model.user.entity.Admin;
import com.springboot.test.model.user.entity.User;
import com.springboot.test.service.AdminService;
import com.springboot.test.threadlocal.ThreadLocalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhoujian
 * @date 2020/1/20
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private ThreadLocalService threadLocalService;

    private volatile AtomicInteger count= new AtomicInteger(0);


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException {

        User user = new User();
        user.setName("zj");
        user.setAge(10);
        ThreadLocal<User> threadLocal = ThreadLocalService.threadLocal;
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set(user);
                threadLocalService.getThreadLocal(1);
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set(user);
                threadLocalService.getThreadLocal(2);
            }
        });

        t1.start();

        user.setName("aaa");
        user.setAge(50);
        t2.start();

        return "test";
    }


    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult get(Admin admin) throws Exception {

        JsonResult rsult=new JsonResult();
        CountDownLatch countDownLatch=new CountDownLatch(10);
        for(int i=1;i<=10;i++){
            new Thread(()->{
                try {
                    Thread.sleep((long)Math.random());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(int j=0;j<100;j++){
                    count.incrementAndGet();
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        rsult.setMsg("此次数量为:"+count.get());
        rsult.setCode("0000");
        return rsult;
    }

}
