package com.springboot.test.controller;

import com.springboot.test.model.entity.User;
import com.springboot.test.service.AdminService;
import com.springboot.test.threadlocal.ThreadLocalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException {

        User user=new User();
        user.setName("zj");
        user.setAge(10);
        ThreadLocal<User> threadLocal = ThreadLocalService.threadLocal;
         Thread t1=new Thread(new Runnable() {
             @Override
             public void run() {
                 threadLocal.set(user);
                 threadLocalService.getThreadLocal(1);
             }
         });

        Thread t2=new Thread(new Runnable() {
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

}
