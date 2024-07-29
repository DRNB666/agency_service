package com.leepsmart.code.common.mybatisplus.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author leepsmart
 */
@Component
@ConfigurationProperties(prefix = "mybatis-plus.tenant")
public class TenantBean {

    /**
     * 是否开启多租户模式
     */
    private Boolean enable;

    /**
     * 租户id字段名
     */
    private String fieldId;

    /**
     * 过滤不需要租户隔离的表集合
     */
    private List<String> ignoreTables;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public List<String> getIgnoreTables() {
        return ignoreTables;
    }

    public void setIgnoreTables(List<String> ignoreTables) {
        this.ignoreTables = ignoreTables;
    }
}
