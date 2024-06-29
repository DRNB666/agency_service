package com.turing.code.common.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


@Configuration
public class InterceptorWebMvcConfigurer implements WebMvcConfigurer {

    @Resource
    private TenantInterceptor tenantInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 拦截器按照顺序执行
         *addPathPatterns 用于添加拦截规则
         *excludePathPatterns 用于排除拦截
         */
        registry.addInterceptor(tenantInterceptor)
                .addPathPatterns("/tenant/**")
                .excludePathPatterns("/tenant/public/**");

    }
}


