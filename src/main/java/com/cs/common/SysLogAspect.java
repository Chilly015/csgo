package com.cs.common;

import com.cs.common.annotation.RequiredLog;
import com.cs.common.utils.IPUtils;
import com.cs.sys.entity.SysLog;
import com.cs.sys.service.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

@Component
@Aspect
public class SysLogAspect {
    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(com.cs.common.annotation.RequiredLog)")
    public void doCache(){}
    @Around("doCache()")
    public Object around(ProceedingJoinPoint jp){
        Object result = null;
        try {
            long a = System.currentTimeMillis();
            result = jp.proceed();
            long b = System.currentTimeMillis();
            saveLogObject(jp,(b-a));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

    private void saveLogObject(ProceedingJoinPoint jp, long time) throws Exception {
        Class<?> cls = jp.getTarget().getClass();
        String clsName = cls.getName();
        MethodSignature ms = (MethodSignature) jp.getSignature();
        String methodName = ms.getName();
        String method = clsName+"."+methodName;
        Method declaredMethod = cls.getDeclaredMethod(methodName, ms.getParameterTypes());
        RequiredLog ano = declaredMethod.getDeclaredAnnotation(RequiredLog.class);
        String operation = methodName;
        if(!StringUtils.isEmpty(ano.value())&&ano!=null){
            operation=ano.value();
        }
        String ip = IPUtils.getIpAddr();
        String params = Arrays.toString(jp.getArgs());
        SysLog sysLog = new SysLog();
        sysLog.setUsername("xcw");
        sysLog.setMethod(method);
        sysLog.setOperation(operation);
        sysLog.setParams(params);
        sysLog.setTime(time);
        sysLog.setIp("192.168.0.1");
        sysLog.setCreatedTime(new Date());
        System.out.println("logAspect:"+Thread.currentThread().getName());
        /*new Thread(){
            @Override
            public void run() {
                sysLogService.saveLogObject(sysLog);
            }
        }.start();*/
        sysLogService.saveLogObject(sysLog);
    }
}
