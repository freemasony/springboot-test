package com.springboot.test.repository;

import com.springboot.test.model.entity.SysJobTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SysJobTaskDao extends JpaRepository<SysJobTask, Long>, JpaSpecificationExecutor<SysJobTask> {
    List<SysJobTask> findByStatus(Integer status);
}
