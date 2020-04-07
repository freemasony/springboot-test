package com.springboot.test.singleton;

/**
 * 饿汉式
 * @author zhoujian
 * @date 2020/3/9
 */
public class Singleton2 {

    private static Singleton2 Singleton2=new Singleton2();

    public static Singleton2 getInstance(){
        return Singleton2;
    }
}
