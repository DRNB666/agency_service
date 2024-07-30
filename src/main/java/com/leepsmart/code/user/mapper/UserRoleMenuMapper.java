package com.leepsmart.code.user.mapper;

import com.leepsmart.code.user.pojo.UserMenu;
import com.leepsmart.code.user.pojo.UserRoleMenu;
import com.leepsmart.code.common.mybatisplus.methods.CommonMapper;

import java.util.List;

/**
 * @author leepsmart generator
 * @since 2024-07-30
 */
public interface UserRoleMenuMapper extends CommonMapper<UserRoleMenu> {

    List<UserMenu> selectRoleMenu(UserRoleMenu adminRoleMenu);
}
