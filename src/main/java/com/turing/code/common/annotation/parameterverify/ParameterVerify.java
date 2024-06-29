package com.turing.code.common.annotation.parameterverify;

import java.lang.annotation.*;

/**
 * @title 请求参数验证
 * @author: turing
 * 当前支持直接验证的对象为: String, Integer, Double, Long, File, MultipartFile
 * 后续需要请自行加上
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParameterVerify {

    /**
     * 验证的非空参数
     */
    String[] notNull() default "";

    /**
     * 验证手机号码参数
     */
    String[] isPhone() default "";

    /**
     * 校验身份证号参数
     */
    String[] isIdCard() default "";

    /**
     * 长度校验
     */
    Length[] length() default {};

}
