package com.springboot.test.listener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

/**
 * @author zhoujian
 * @date 2020/4/28
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyEvent implements Serializable {

    private Long customerId;

    private String customerName;

    private Integer company;


}
