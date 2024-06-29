package com.turing.code.common.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * 读取config.properties工具类
 *
 * @author xiaofeixia
 * @date 2016/9/15
 */
public class PropertyUtil {
    private static Properties smsProps;

    static {
        loadProps();
    }

    synchronized static private void loadProps() {
        LogUtil.info("开始加载properties文件内容.......");
        smsProps = new Properties();
        InputStream in = null;
        try {
            //第一种，通过类加载器进行获取properties文件流
            in = PropertyUtil.class.getClassLoader().getResourceAsStream("config.properties");
            smsProps.load(new InputStreamReader(in, StandardCharsets.UTF_8));
            // 第二种，通过类进行获取properties文件流
            smsProps.load(in);
        } catch (FileNotFoundException e) {
            LogUtil.info("文件未找到");
        } catch (IOException e) {
            LogUtil.info("出现IOException");
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                LogUtil.info("文件流关闭出现异常");
            }
        }
        LogUtil.info("加载properties文件内容完成...........");
        LogUtil.info("properties文件内容：" + smsProps);
    }

    public static String getSmsProperty(String key) {
        if (null == smsProps) {
            loadProps();
        }
        return smsProps.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if (null == smsProps) {
            loadProps();
        }
        return smsProps.getProperty(key, defaultValue);
    }
}
