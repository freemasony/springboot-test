package com.springboot.test.repository.product;

import com.springboot.test.model.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author zhoujian
 * @date 2020/4/7
 */
public interface ProductDao extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
}
