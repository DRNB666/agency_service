package com.turing.code.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.tenant.pojo.vo.TenantUserVo;
import com.turing.code.user.pojo.UserBelongTenant;
import com.turing.code.common.mybatisplus.methods.CommonService;

import java.util.HashMap;

/**
 * @author turing generator
 * @since 2024-06-18
 */
public interface UserBelongTenantService extends CommonService<UserBelongTenant> {

    IPage<TenantUserVo> userList(Page<TenantUserVo> page, HashMap<String, Object> stringObjectHashMap);
}
