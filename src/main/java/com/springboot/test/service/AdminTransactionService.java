package com.springboot.test.service;

import com.springboot.test.model.user.entity.Admin;
import com.springboot.test.repository.user.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhoujian
 * @date 2020/3/10
 */
@Component
public class AdminTransactionService {

    @Autowired
    private AdminDao adminDao;

    public void saveTwo(){
        Admin admin = new Admin();
        admin.setUserId(2L);
        admin.setUserName("李四");
        adminDao.save(admin);
        throw new RuntimeException();
    }
}
