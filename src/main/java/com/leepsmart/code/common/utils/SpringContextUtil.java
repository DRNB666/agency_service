package com.leepsmart.code.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware {

    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 根据指定bean名称，获取对应的实例对象
     *
     * @param name bean的名称
     * @return
     * @throws BeansException
     */
    public static Object getBean(String name) throws BeansException {
        if (applicationContext == null) {
            throw new RuntimeException("spring 上下文对象未初始化，无法完成bean的查找！");
        }

        return applicationContext.getBean(name);
    }

    /**
     * 根据指定Bean类型，获取对应的实例对象
     *
     * @param requiredType bean的class类型
     * @return
     * @throws BeansException
     */
    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        if (applicationContext == null) {
            throw new RuntimeException("spring 上下文对象未初始化，无法完成bean的查找！");
        }

        return applicationContext.getBean(requiredType);
    }
}
