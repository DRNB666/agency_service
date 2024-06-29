package com.turing.code.tenant.mapper;

import com.turing.code.tenant.pojo.TenantInviteConfig;
import com.turing.code.common.mybatisplus.methods.CommonMapper;

/**
 * @author turing generator
 * @since 2024-06-01
 */
public interface TenantInviteConfigMapper extends CommonMapper<TenantInviteConfig> {

    TenantInviteConfig selectByMerchantId(Integer tenantId);
}
