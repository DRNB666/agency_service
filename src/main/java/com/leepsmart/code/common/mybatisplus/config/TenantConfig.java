package com.leepsmart.code.common.mybatisplus.config;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.leepsmart.code.common.mybatisplus.pojo.TenantBean;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 租户模式配置
 *
 * @author leepsmart
 */
@Configuration
public class TenantConfig implements TenantLineHandler {

    @Resource
    private HttpServletRequest request;
    @Resource
    private TenantBean tenantBean;

    /**
     * 获取当前租户id
     *
     * @return
     */
    @Override
    public Expression getTenantId() {
        Object tenantId;
        try {
            tenantId = request.getAttribute("tenantId");
        } catch (Exception e) {
            return new NullValue();
        }
        if (tenantId == null) {
            return new NullValue();
        }
        System.out.println("当前租户id:" + tenantId);
        return new LongValue((Integer) tenantId);
    }

    /**
     * 设置租户id字段名
     *
     * @return
     */
    @Override
    public String getTenantIdColumn() {
        return tenantBean.getFieldId();
    }

    /**
     * 根据表名判断是否需要过滤
     *
     * @param tableName
     * @return
     */
    @Override
    public boolean ignoreTable(String tableName) {
        Object tenantId;
        try {
            tenantId = request.getAttribute("tenantId");
        } catch (Exception e) {
            return true;
        }
        if (tenantId != null && tenantId.equals(1)) {
            // 如果租户id为1则是管理员, 对所有表不执行租户模式
            return true;
        }
        return tenantBean.getIgnoreTables().stream().anyMatch(item -> item.equals(tableName));
    }

}