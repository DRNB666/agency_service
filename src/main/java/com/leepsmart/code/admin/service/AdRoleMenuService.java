package com.leepsmart.code.admin.service;

import com.leepsmart.code.admin.pojo.AdMenu;
import com.leepsmart.code.admin.pojo.AdRoleMenu;
import com.leepsmart.code.common.mybatisplus.methods.CommonService;

import java.util.List;

/**
 * @author leepsmart generator
 * @since 2021-02-03
 */
public interface AdRoleMenuService extends CommonService<AdRoleMenu> {

    String setRoleMenu(Integer roleId, Integer[] menuIds);

    List<AdMenu> selectRoleMenu(AdRoleMenu adminRoleMenu);

}
