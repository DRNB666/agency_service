package com.leepsmart.code.admin.mapper;

import com.leepsmart.code.admin.pojo.AdMenu;
import com.leepsmart.code.admin.pojo.AdRoleMenu;
import com.leepsmart.code.common.mybatisplus.methods.CommonMapper;

import java.util.List;

/**
 * @author leepsmart generator
 * @since 2021-02-03
 */
public interface AdRoleMenuMapper extends CommonMapper<AdRoleMenu> {

    List<AdMenu> selectRoleMenu(AdRoleMenu record);

}
