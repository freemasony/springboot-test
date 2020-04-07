package com.springboot.test.aop;

import com.springboot.test.common.JsonResult;
import com.springboot.test.common.JsonSerializeUtil;
import com.springboot.test.service.AdminService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhoujian
 * @date 2020/3/2
 */
@Aspect
@Component
public class OperateLogAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private AsyncTaskExecutor taskExecutor;

    @Resource
    private AdminService adminService;

    /**
     * 此处的切点是注解的方式，也可以用包名的方式达到相同的效果
     * '@Pointcut("execution(* com.wwj.springboot.service.impl.*.*(..))")'
     */
    @Pointcut("@annotation(com.springboot.test.aop.OperateLog)")
    public void operationLog() {
    }


    /**
     * 环绕增强，相当于MethodInterceptor
     */
    @Around("operationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        OperateLog annotation= signature.getMethod().getAnnotation(OperateLog.class);
        Object res = null;
        long time = System.currentTimeMillis();
        res = joinPoint.proceed();
        time = System.currentTimeMillis() - time;
        logger.info("OperateType:"+annotation.remark()+";Around-time:" + time);
        return res;
    }

    @Before("operationLog()")
    public void doBeforeAdvice(JoinPoint joinPoint){
        logger.info("进入方法前执行.....");
    }

    /**
     * 处理完请求，返回内容
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "operationLog()")
    public void doAfterReturning(JoinPoint joinPoint,Object ret) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        OperateLog annotation= signature.getMethod().getAnnotation(OperateLog.class);
        logger.info("方法的返回值 : " + JsonSerializeUtil.objectToJson((JsonResult)ret));
        for(int i=0;i<=8;i++){
            taskExecutor.execute(()-> {
                try {
                    adminService.test();
                } catch (Exception e) {
                    logger.error("doAfterReturningError",e);
                }
            });
        }



    }

    /**
     * 后置异常通知
     */
    @AfterThrowing("operationLog()")
    public void throwss(JoinPoint jp){
        logger.info("方法异常时执行.....");
    }


    /**
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     */
    @After("operationLog()")
    public void after(JoinPoint jp){
        logger.info("方法最后执行.....");
    }
}
