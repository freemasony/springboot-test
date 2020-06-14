package com.springboot.test.repository.user;

import com.springboot.test.model.user.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminDao extends JpaRepository<Admin, Long>, JpaSpecificationExecutor<Admin> {
}
