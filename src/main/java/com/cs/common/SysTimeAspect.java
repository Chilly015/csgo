/*
package com.cs.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(2)
@Service
@Aspect
public class SysTimeAspect {
    @Pointcut("bean(sysLogServiceImpl)")
    public void doTime() {
    }

    @Before("doTime()")
    public void doBefore(JoinPoint jp) {
        System.out.println("time doBefore()");
    }

    @After("doTime()")
    public void doAfter() {
        System.out.println("time doAfter()");
    }

    */
/**
     * 核心业务正常结束时执行
     * 说明：假如有after，先执行after,再执行returning
     *//*

    @AfterReturning("doTime()")
    public void doAfterReturning() {
        System.out.println("time doAfterReturning");
    }

    */
/**
     * 核心业务出现异常时执行
     * 说明：假如有after，先执行after,再执行Throwing
     *//*

    @AfterThrowing("doTime()")
    public void doAfterThrowing() {
        System.out.println("time doAfterThrowing");
    }

    @Around("doTime()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("我是order2");
        Object obj = pjp.proceed();
        System.out.println("doAround.after");
        return obj;
    }
}
*/
