package com.iweb.xt.web;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectTest {

//    @Pointcut("execution( com.iweb.xt.web.api.NewsController.*)")


    @Around("execution(* com.iweb.xt.web.api.NewsController.*.*(..))")
    public Object around(ProceedingJoinPoint pjp){
        System.out.println("cache=========================================");
            Signature signature = pjp.getSignature();
        System.out.println(signature.getName());
//            类名
        return "abcd";
    }
}
