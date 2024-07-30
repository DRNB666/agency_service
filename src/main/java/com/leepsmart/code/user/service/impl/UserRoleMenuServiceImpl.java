package com.leepsmart.code.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import com.leepsmart.code.common.ex.ServiceException;
import com.leepsmart.code.common.mybatisplus.structure.CustomQueryWrapper;
import com.leepsmart.code.common.redis.utils.RedisUtil;
import com.leepsmart.code.common.utils.returnbody.ReturnBody;
import com.leepsmart.code.user.pojo.UserMenu;
import com.leepsmart.code.user.pojo.UserRoleMenu;
import com.leepsmart.code.user.mapper.UserRoleMenuMapper;
import com.leepsmart.code.user.service.UserRoleMenuService;
import com.leepsmart.code.common.mybatisplus.methods.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author leepsmart generator
 * @since 2024-07-30
 */
@Service
public class UserRoleMenuServiceImpl extends CommonServiceImpl<UserRoleMenuMapper, UserRoleMenu> implements UserRoleMenuService {

    @Transactional
    @Override
    public String setRoleMenu(Integer roleId, Integer[] menuIds) {
        // 查询该角色拥有权限的菜单
        QueryWrapper<UserRoleMenu> queryWrapper = new CustomQueryWrapper<>();
        queryWrapper.eq(UserRoleMenu.ROLE_ID, roleId);
        List<UserRoleMenu> list = super.list(queryWrapper);
        if (list.size() > 0) {
            // 删除该角色原有的菜单权限
            if (!super.remove(queryWrapper)) {
                throw new ServiceException("删除菜单权限失败");
            }
        }
        list = new ArrayList<>();
        UserRoleMenu UserRoleMenu;
        // 新增菜单权限
        for (Integer menuId : menuIds) {
            UserRoleMenu = new UserRoleMenu();
            UserRoleMenu.setRoleId(roleId);
            UserRoleMenu.setMenuId(menuId);
            list.add(UserRoleMenu);
        }
        // 插入菜单权限
        if (!super.saveBatch(list)) {
            throw new ServiceException("新增菜单权限失败");
        }
        return ReturnBody.success();
    }


    @Override
    public List<UserMenu> selectRoleMenu(UserRoleMenu adminRoleMenu) {
        return baseMapper.selectRoleMenu(adminRoleMenu);
    }
}
