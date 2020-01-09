package com.springboot.test.model;

import com.springboot.test.model.entity.User;
import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserInfo {

    private String city;

    private String payDay;

    private List<User> userList;
}
