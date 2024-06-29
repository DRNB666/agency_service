package com.turing.code.tenant.mapper;

import com.turing.code.tenant.pojo.TenantProIdentifi;
import com.turing.code.common.mybatisplus.methods.CommonMapper;

import java.util.List;

/**
 * @author turing generator
 * @since 2024-06-06
 */
public interface TenantProIdentifiMapper extends CommonMapper<TenantProIdentifi> {

    List<TenantProIdentifi> selectSelective(TenantProIdentifi setStatus);

}
