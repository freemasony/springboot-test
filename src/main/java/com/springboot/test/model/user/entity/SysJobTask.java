package com.springboot.test.model.user.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author zhoujian
 */
@Entity
@Table(name = "sys_job_task")
@Getter
@Setter
public class SysJobTask {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "bean_name")
  private String beanName;

  @Column(name = "method_name")
  private String methodName;

  @Column(name = "method_params")
  private String methodParams;

  @Column(name = "cron_expres")
  private String cronExpres;

  @Column(name = "status")
  private long status;

  @Column(name = "remark")
  private String remark;

  @Column(name = "create_time")
  private Timestamp createTime;

  @Column(name = "update_time")
  private Timestamp updateTime;


}
