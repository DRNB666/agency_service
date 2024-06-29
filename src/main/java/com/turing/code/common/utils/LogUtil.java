package com.turing.code.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 */
public class LogUtil {

    /**
     * info打印
     *
     * @param s     字符串，中间有参数用{}占位
     * @param param 填充占位符的参数
     */
    public static void info(String s, Object... param) {
        getLogger().info(s, param);
    }

    public static void info(Object o) {


//        System.out.println(o.toString());

        getLogger().info(o.toString());
    }


    /**
     * warn警告
     *
     * @param s     字符串，中间有参数用{}占位
     * @param param 填充占位符的参数
     */
    public static void warn(String s, Object... param) {
        getLogger().warn(s, param);
    }

    public static void warn(Object o) {
        getLogger().warn(o.toString());
    }

    /**
     * debug
     *
     * @param s     字符串，中间有参数用{}占位
     * @param param 填充占位符的参数
     */
    public static void debug(String s, Object... param) {
        getLogger().debug(s, param);
    }

    public static void debug(Object o) {
        getLogger().debug(o.toString());
    }


    /**
     * error错误
     *
     * @param s     字符串，中间有参数用{}占位
     * @param param 填充占位符的参数
     */
    public static void error(String s, Object... param) {
        getLogger().error(s, param);
    }

    public static void error(Object o) {
        getLogger().error(o.toString());
    }

    /**
     * 获取logger对象
     *
     * @return logger对象
     */
    private static Logger getLogger() {
        String classname = new Exception().getStackTrace()[1].getClassName();
        return LoggerFactory.getLogger(classname);
    }
}
