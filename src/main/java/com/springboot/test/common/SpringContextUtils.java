package com.springboot.test.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


/**
 * @author zhoujian
 */
@Component
@Lazy(false)
public class SpringContextUtils implements ApplicationContextAware
{

	public static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException
	{
		SpringContextUtils.context = context;
	}

	public static <T> T getBean(String beanId, Class<T> clazz)
	{
		return context.getBean(beanId, clazz);
	}

	public static Object getBean(String beanName){
		return context.getBean(beanName);
	}

	public static ApplicationContext getContext()
	{
		return context;
	}

}
