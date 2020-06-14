package com.springboot.test.config.dbconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author zhoujian
 * @date 2020/4/2
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.springboot.test.repository.user"},
        entityManagerFactoryRef = "userEntityManagerFactory",
        transactionManagerRef = "userTransactionManager")
public class UserJpaConfig {

    @Autowired
    @Qualifier("userDataSource")
    private DataSource dataSource;



    @Bean("userJpaProperties")
    @Primary
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }


    @Primary
    @Bean(name="userEntityManager")
    public EntityManager entityManager(@Qualifier("userJpaProperties") JpaProperties jpaProperties,
                                       EntityManagerFactoryBuilder builder){
        return entityManagerFactory(jpaProperties,builder).getObject().createEntityManager();
    }



    @Bean("userEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(JpaProperties jpaProperties,
                                                                       EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource)
                .properties(jpaProperties.getProperties())
                .persistenceUnit("userPersistenceUnit")
                .packages("com.springboot.test.model.user")
                .build();
    }

    @Bean("userTransactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(@Qualifier("userEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
