package com.turing.code.common.annotation.parameterverify;

/**
 * @author turing
 */
public @interface Length {

    /**
     * 参数名
     */
    String name() default "";

    /**
     * 最小长度
     */
    int min() default -1;

    /**
     * 最大长度
     */
    int max() default -1;

    /**
     * 限制长度
     */
    int limit() default -1;

}
