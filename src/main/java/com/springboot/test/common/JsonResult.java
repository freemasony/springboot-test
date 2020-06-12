package com.springboot.test.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author user
 */

@Getter
@Setter
@NoArgsConstructor
public class JsonResult<T> implements Serializable {

    private String code;

    private String msg;

    private Map<String,T> data=new HashMap<>();

    public static JsonResult  success(){
        Map<String,String> data=new HashMap<>();
        JsonResult result=new JsonResult();
        result.setCode("200");
        result.setMsg("成功");
        result.setData(data);
        return result;
    }
}
