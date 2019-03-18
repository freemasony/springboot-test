package com.springboot.test.service;


import com.springboot.test.config.JdbcConfig;
import com.springboot.test.model.entity.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author user
 */
@Component
public class AdminService {

    private Logger logger= LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Admin> query(String username,String password){
        String sql="select * from admin where username='"+username+"' and password = '"+password+"' ";

        List<Admin> admin=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Admin.class));

        logger.info("sql语句执行成功，"+sql);
        logger.info("admin:，"+admin);

        return admin;

    }

}
