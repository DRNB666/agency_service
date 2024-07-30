package com.leepsmart.code.user.service;

import com.leepsmart.code.admin.pojo.AdMenu;
import com.leepsmart.code.admin.pojo.AdRoleMenu;
import com.leepsmart.code.user.pojo.UserMenu;
import com.leepsmart.code.user.pojo.UserRoleMenu;
import com.leepsmart.code.common.mybatisplus.methods.CommonService;

import java.util.List;

/**
 * @author leepsmart generator
 * @since 2024-07-30
 */
public interface UserRoleMenuService extends CommonService<UserRoleMenu> {

    String setRoleMenu(Integer roleId, Integer[] menuIds);

    List<UserMenu> selectRoleMenu(UserRoleMenu userRoleMenu);
}
