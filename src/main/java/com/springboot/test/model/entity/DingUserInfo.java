package com.springboot.test.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author zhoujian
 * @date 2019/9/18
 */
@Getter
@Setter
@AllArgsConstructor
public class DingUserInfo implements Serializable {

    private String mobile;

    private String name;

    private String email;

    private String userId;

    private String departMendId;

    private String departMentName;

    @Override
    public String toString() {
        return "{\"mobile\":"+mobile+",\"name\":\""+name+"\",\"email\":\""+email+"\",\"userId\":\""+userId+"\",\"departMendId\":\""+departMendId+"\",\"departMentName\":\""+departMentName+"\"}";
    }
}
