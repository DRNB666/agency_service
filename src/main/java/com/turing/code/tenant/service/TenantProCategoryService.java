package com.turing.code.tenant.service;

import com.turing.code.tenant.pojo.TenantProCategory;
import com.turing.code.common.mybatisplus.methods.CommonService;

/**
 * @author turing generator
 * @since 2024-06-06
 */
public interface TenantProCategoryService extends CommonService<TenantProCategory> {

    void delete(Long id);
}
