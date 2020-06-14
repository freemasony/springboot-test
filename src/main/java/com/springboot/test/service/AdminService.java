package com.springboot.test.service;


import com.springboot.test.model.user.entity.Admin;
import com.springboot.test.repository.user.AdminDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * @author user
 */
@Component
public class AdminService {

    private Logger logger= LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AdminDao adminDao;


    public List<Admin> query(String username,String password){
        String sql="select * from admin where username='"+username+"' and password = '"+password+"' ";

        List<Admin> admin=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Admin.class));

        logger.info("sql语句执行成功，"+sql);
        logger.info("admin:，"+admin);

        return admin;

    }


    public void test() throws Exception{
        Thread.sleep(1500* new Random().nextInt(10));
        System.out.println("当前线程："+Thread.currentThread().getName());
    }

    public void saveOne(){
        Admin admin=new Admin();
        admin.setUserId(1L);
        admin.setUserName("张三");
        adminDao.save(admin);
    }

    public List<Admin> list(){
        return adminDao.findAll();
    }

}
