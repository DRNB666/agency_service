package com.turing.code.tenant.mapper;

import com.turing.code.tenant.pojo.TenantMenu;
import com.turing.code.tenant.pojo.TenantRoleMenu;
import com.turing.code.common.mybatisplus.methods.CommonMapper;

import java.util.List;

/**
 * @author turing generator
 * @since 2024-05-28
 */
public interface TenantRoleMenuMapper extends CommonMapper<TenantRoleMenu> {

    List<TenantMenu> selectRoleMenu(TenantRoleMenu tenantRoleMenu);
}
