package com.springboot.test.service;

import com.springboot.test.model.product.entity.Product;
import com.springboot.test.repository.product.ProductDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhoujian
 * @date 2020/4/7
 */
@Slf4j
@Component
public class ProductService {
    @Autowired
    private ProductDao productDao;

    public List<Product> findAll() {
        return  productDao.findAll();
    }
}
