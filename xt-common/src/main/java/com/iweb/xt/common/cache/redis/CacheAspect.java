package com.iweb.xt.common.cache.redis;

import com.alibaba.fastjson.JSON;
import com.iweb.xt.common.cache.Cache;
import com.iweb.xt.common.model.CallResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class CacheAspect {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Pointcut("@annotation(com.iweb.xt.common.cache.Cache)")
    public void pt(){}
    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp){
        System.out.println("cache=========================================");
        try {
            Signature signature = pjp.getSignature();
//            类名
            String className = pjp.getTarget().getClass().getSimpleName();
//            调用方法名
            String methodName = signature.getName();

            Class[] parameterTypes = new Class[pjp.getArgs().length];
            Object[] args = pjp.getArgs();
//            参数
            String params = "";
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null) {
                    params += JSON.toJSONString(args[i]);
                    parameterTypes[i] = args[i].getClass();
                }else {
                    parameterTypes[i] = null;
                }
            }
            if (StringUtils.isNotEmpty(params)){
//                加密 以防出现key过长以及字符转义获取不到的情况
                params = DigestUtils.md5Hex(params);
            }
            Method method = pjp.getSignature().getDeclaringType().getMethod(methodName,parameterTypes);
//            获取cache注解
            Cache annotation = method.getAnnotation(Cache.class);
//            缓存过期时间
            String name = annotation.name();
            String redisKey = name + "::" + className + "::" + methodName+ "::" + params;
            long time = annotation.time();
            // Check cache first
            String cachedResult = redisTemplate.opsForValue().get(redisKey);
            System.out.println(cachedResult);
            if (StringUtils.isNotBlank(cachedResult)) {
                //走缓存
                log.info("走了缓存~~~{}--{}", className,methodName);
                CallResult callResult = JSON.parseObject(cachedResult,CallResult.class);
                return callResult;
            }
            // Proceed with the method invocation
            Object proceed = pjp.proceed();
            // Cache the result
            redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(proceed), time, TimeUnit.SECONDS);
            log.info("存入缓存~~~ {}--{}", className,methodName);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return CallResult.fail();
    }
}
