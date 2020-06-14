package com.springboot.test.model.vo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhoujian
 * @date 2019/6/5
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PushInfo implements Serializable {
    private String message;
    private int count;
    private String date;
}
