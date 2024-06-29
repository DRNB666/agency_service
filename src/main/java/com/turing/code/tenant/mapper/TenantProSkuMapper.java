package com.turing.code.tenant.mapper;

import com.turing.code.tenant.pojo.TenantProSku;
import com.turing.code.common.mybatisplus.methods.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author turing generator
 * @since 2024-06-06
 */
public interface TenantProSkuMapper extends CommonMapper<TenantProSku> {

    int insertList(@Param("proSkus") List<TenantProSku> proSkus);

    int deleteByProIdAndType(@Param("proId") Long proId, @Param("type") Integer type);
}
