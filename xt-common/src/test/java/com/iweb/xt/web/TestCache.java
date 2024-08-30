package com.iweb.xt.web;

import com.iweb.xt.common.cache.Cache;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class TestCache {

    @SneakyThrows
    @Test
    public void test(){
        Class<?> c = Class.forName("com.iweb.xt.web.GetCache");

        // 获取目标方法的Method对象
        Method method = c.getMethod("isCache");

        // 获取方法上的Cache注解
        if (method.isAnnotationPresent(Cache.class)) {
            Cache cache = method.getAnnotation(Cache.class);
            System.out.println("Cache Name: " + cache.name());
            System.out.println("Cache Time: " + cache.time());
        } else {
            System.out.println("No Cache annotation present on the method.");
        }
    }
}
