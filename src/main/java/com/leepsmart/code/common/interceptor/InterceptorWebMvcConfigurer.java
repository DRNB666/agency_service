package com.leepsmart.code.common.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


@Configuration
public class InterceptorWebMvcConfigurer implements WebMvcConfigurer {

    @Resource
    private AdminInterceptor adminInterceptor;
    @Resource
    private UsInterceptor usInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/public/**");

        registry.addInterceptor(usInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/public/**");

    }
}


