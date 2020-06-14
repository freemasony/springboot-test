package com.springboot.test.config.dbconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableJpaRepositories(basePackages = {"com.springboot.test.repository.product"},
        entityManagerFactoryRef = "productEntityManagerFactory",
        transactionManagerRef = "productTransactionManager")
public class ProductJpaConfig {

    @Autowired
    @Qualifier("productDataSource")
    private DataSource dataSource;

    @Bean("productJpaProperties")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    @Bean(name="productEntityManager")
    public EntityManager entityManager(@Qualifier("productJpaProperties") JpaProperties jpaProperties,
                                       EntityManagerFactoryBuilder builder){
        return entityManagerFactory(jpaProperties,builder).getObject().createEntityManager();
    }

    @Bean("productEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(JpaProperties jpaProperties,
                                                                       EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource)
                .properties(jpaProperties.getProperties())
                .persistenceUnit("productPersistenceUnit")
                .packages("com.springboot.test.model.product.entity")
                .build();
    }

    @Bean("productTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("productEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
