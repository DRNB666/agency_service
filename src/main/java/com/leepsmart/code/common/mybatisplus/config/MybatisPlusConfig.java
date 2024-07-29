package com.leepsmart.code.common.mybatisplus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.leepsmart.code.common.mybatisplus.pojo.TenantBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author leepsmart
 * mybatis-plus bean 配置
 */
@Configuration
@MapperScan("com.leepsmart.*.*.mapper")
public class MybatisPlusConfig {

    @Resource
    private TenantConfig tenantConfig;
    @Resource
    private TenantBean tenantBean;

    /**
     * 配置 mybatis-plus 插件
     *
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 乐观锁配置
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

        // 防止恶意全表更新和全表删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        // 是否开启多租户模式
        if (tenantBean.getEnable()) {
            // 加载多租户模式配置
            interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(tenantConfig));
        }

        // 分页插件设置
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }

    /**
     * 自定义sql注入器
     *
     * @return
     */
    @Bean
    public CustomerSqlInjector customerSqlInjector() {
        return new CustomerSqlInjector();
    }

}
