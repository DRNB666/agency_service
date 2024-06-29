package com.turing.code.tenant.service;

import com.turing.code.tenant.pojo.TenantInfo;
import com.turing.code.common.mybatisplus.methods.CommonService;

/**
 * @author turing generator
 * @since 2024-05-27
 */
public interface TenantInfoService extends CommonService<TenantInfo> {

    String authority(String account, String password);
}
