package com.turing.code.tenant.mapper;

import com.turing.code.tenant.pojo.TenantProCategory;
import com.turing.code.tenant.pojo.TenantShopCategory;
import com.turing.code.common.mybatisplus.methods.CommonMapper;

import java.util.List;

/**
 * @author turing generator
 * @since 2024-06-06
 */
public interface TenantShopCategoryMapper extends CommonMapper<TenantShopCategory> {

    List<TenantProCategory> selectInCache();
}
