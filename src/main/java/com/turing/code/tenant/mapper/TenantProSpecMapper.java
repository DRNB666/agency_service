package com.turing.code.tenant.mapper;

import com.turing.code.tenant.pojo.TenantProSpec;
import com.turing.code.common.mybatisplus.methods.CommonMapper;

/**
 * @author turing generator
 * @since 2024-06-06
 */
public interface TenantProSpecMapper extends CommonMapper<TenantProSpec> {

    int deleteByProIdAndStatus(Long id, int type);
}
