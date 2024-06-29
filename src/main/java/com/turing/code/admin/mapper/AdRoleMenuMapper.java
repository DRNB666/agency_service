package com.turing.code.admin.mapper;

import com.turing.code.admin.pojo.AdMenu;
import com.turing.code.admin.pojo.AdRoleMenu;
import com.turing.code.common.mybatisplus.methods.CommonMapper;

import java.util.List;

/**
 * @author turing generator
 * @since 2021-02-03
 */
public interface AdRoleMenuMapper extends CommonMapper<AdRoleMenu> {

    List<AdMenu> selectRoleMenu(AdRoleMenu record);

}
