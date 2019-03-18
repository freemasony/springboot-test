package com.springboot.test.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author user
 */
@Entity
@Table(name = "admin")
@Getter
@Setter
public class Admin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userid")
    private Long userId;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;


    @Column(name = "age")
    private Integer age;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "type")
    private Integer type;

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", createTime=" + createTime +
                ", type=" + type +
                '}';
    }
}
