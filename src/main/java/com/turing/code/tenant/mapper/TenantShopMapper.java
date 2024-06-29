package com.turing.code.tenant.mapper;

import com.turing.code.tenant.pojo.TenantShop;
import com.turing.code.common.mybatisplus.methods.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author turing generator
 * @since 2024-06-01
 */
public interface TenantShopMapper extends CommonMapper<TenantShop> {

    List<TenantShop> selectSelective(@Param("tenantShop") TenantShop setTenantId);
    List<Map<String, Object>> selectMap(@Param("map") Map<String, Object> map);
}
