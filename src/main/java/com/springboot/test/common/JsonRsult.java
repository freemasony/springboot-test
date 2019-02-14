package com.springboot.test.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author user
 */

@Getter
@Setter
public class JsonRsult<T> implements Serializable {

    private String code;

    private String msg;

    private Map<String,T> data=new HashMap<>();
}
