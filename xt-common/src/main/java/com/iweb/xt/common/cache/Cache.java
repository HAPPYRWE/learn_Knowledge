package com.iweb.xt.common.cache;

import java.lang.annotation.*;

@Target(ElementType.METHOD)//作用再方法上
@Retention(RetentionPolicy.RUNTIME)//保留在运行期
@Documented
public @interface Cache {
    /**
     * 缓存前缀名称
     * @return
     */
    String name() default "";

    /**
     * 过期时间
     * @return
     */
    long time() default 60;
}
