package com.springboot.test.config.dbconfig;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author zhoujian
 * @date 2020/4/2
 */
@Configuration
public class DataSourceConfig {


    @Bean("userDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.user")
    public DataSource dataSourceUser(){
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean("userJdbcTemplate")
    public JdbcTemplate userJdbcTemplate(@Qualifier("userDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean("productDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.product")
    public DataSource dataSourceProduct(){
        return DataSourceBuilder.create().build();
    }

    @Bean("productJdbcTemplate")
    public JdbcTemplate productJdbcTemplate(@Qualifier("productDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
