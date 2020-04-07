package com.springboot.test.singleton;

import javafx.beans.binding.When;

/**
 * 饿汉式
 * @author zhoujian
 * @date 2020/3/9
 */
public class Singleton3 {

    private static class SingletonHolder{
        private static final Singleton3 singleton=new Singleton3();
    }

    public static Singleton3 getInstance(){
        return SingletonHolder.singleton;
    }




}
