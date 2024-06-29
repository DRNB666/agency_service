package com.turing.code.tenant.service;

import com.turing.code.tenant.pojo.TenantMenu;
import com.turing.code.tenant.pojo.TenantRoleMenu;
import com.turing.code.common.mybatisplus.methods.CommonService;

import java.util.List;

/**
 * @author turing generator
 * @since 2024-05-28
 */
public interface TenantRoleMenuService extends CommonService<TenantRoleMenu> {

    List<TenantMenu> selectRoleMenu(TenantRoleMenu tenantRoleMenu);

    String setRoleMenu(Integer roleId, Integer[] menuIds);
}
