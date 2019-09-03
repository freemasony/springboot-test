package com.springboot.test.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author zhoujian
 * @date 2019/7/8
 */
@ToString
public class JsonInfo implements Serializable {


    private String name;


    private Integer age;


    private String sex;

    @JsonProperty(value = "name")
    public String getName() {
        return name;
    }

    @JsonProperty(value = "aname")
    public void setAname(String name) {
        this.name = name;
    }

    @JsonProperty(value = "age")
    public Integer getAge() {
        return age;
    }

    @JsonProperty(value = "aage")
    public void setAage(Integer age) {
        this.age = age;
    }

    @JsonProperty(value = "sex")
    public String getSex() {
        return sex;
    }

    @JsonProperty(value = "asex")
    public void setAsex(String sex) {
        this.sex = sex;
    }
}
