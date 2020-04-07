package com.springboot.test.threadlocal;

import com.springboot.test.model.user.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author zhoujian
 * @date 2020/2/21
 */
@Component
public class ThreadLocalService {

    public static ThreadLocal<User> threadLocal=new ThreadLocal();

    public static void setThreadLocal(User user){
        threadLocal.set(user);
    }

    public void getThreadLocal(int i)  {
        User user =threadLocal.get();
        System.out.println("i="+i+":"+user.getName()+":"+user.getAge());
    }
}
