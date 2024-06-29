package com.turing.code.common.annotation.limiter;

import com.google.common.annotations.Beta;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 接口限流注解（可用于Controller类或Controller类下的指定方法，如果同时使用，以方法注解为准）
 *
 * @author Turing
 */
@Beta
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimiter {

    /**
     * 每秒创建令牌个数，默认:10，实际会比限制的多一个
     */
    double qps() default 10D;

    /**
     * 是否加上IP限制
     */
    boolean ipLimit() default false;

    /**
     * 获取令牌等待超时时间 默认:500
     */
    long timeout() default 500;

    /**
     * 超时时间单位 默认:毫秒
     */
    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    /**
     * 无法获取令牌返回提示信息
     */
    String msg() default "亲，您的访问过于频繁，请稍后再试！";

}
