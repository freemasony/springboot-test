package com.springboot.test.aop;

public enum OperateType {

    add(1,"添加"),

    update(2,"更新"),

    query(3,"查询"),

    delete(4,"删除")
    ;

    private Integer code;

    private String value;

    OperateType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }


}
