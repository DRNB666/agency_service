package com.turing.code.admin.service;

import com.turing.code.admin.pojo.AdMenu;
import com.turing.code.admin.pojo.AdRoleMenu;
import com.turing.code.common.mybatisplus.methods.CommonService;

import java.util.List;

/**
 * @author turing generator
 * @since 2021-02-03
 */
public interface AdRoleMenuService extends CommonService<AdRoleMenu> {

    String setRoleMenu(Integer roleId, Integer[] menuIds);

    List<AdMenu> selectRoleMenu(AdRoleMenu adminRoleMenu);

}
