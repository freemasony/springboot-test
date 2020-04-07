package com.springboot.test.singleton;

/**
 * 饱汉式
 * @author zhoujian
 * @date 2020/3/9
 */
public class Singleton1 {

    private static Singleton1 singleton1=null;

    private static volatile Singleton1 singleton=null;

    public static Singleton1 getInstance(){
        if(singleton1==null){
            singleton1=new Singleton1();
        }
        return singleton1;
    }


    public static Singleton1 getSingleton1(){
        if(singleton==null){
            synchronized (Singleton1.class){
                if(singleton == null){
                    singleton=new Singleton1();
                }
            }
        }
        return singleton;
    }
}
