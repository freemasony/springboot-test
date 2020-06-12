package com.springboot.test.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * Created with IntelliJ IDEA
 *
 * @Author zhoujian
 * @Description 导入 Excel 时使用的映射实体类，Excel 模型
 * @Date 2018-12-19
 */
public class ExportDuoInfo extends BaseRowModel {
    @ExcelProperty(value = {"aaa","姓名"} ,index = 0)
    private String name;

    @ExcelProperty(value = {"aaa","年龄"},index = 1)
    private String age;

    @ExcelProperty(value = {"bbb","邮箱"},index = 2)
    private String email;

    @ExcelProperty(value = {"bbb","年龄"},index = 3)
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
