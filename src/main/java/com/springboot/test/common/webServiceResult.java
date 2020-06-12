package com.springboot.test.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhoujian
 * @date 2020/6/12
 */
@Getter
@Setter
public class webServiceResult {
    private int code;

    private String msg;

    private Object data;

    public webServiceResult() {
        this.code = 200;
        this.msg = "成功";
    }

    public static webServiceResult  success(){
        webServiceResult result=new webServiceResult();
        return result;
    }
}
