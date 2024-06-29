package com.turing.code.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.code.admin.mapper.AdRoleMenuMapper;
import com.turing.code.admin.pojo.AdMenu;
import com.turing.code.admin.pojo.AdRoleMenu;
import com.turing.code.admin.service.AdRoleMenuService;
import com.turing.code.common.ex.ServiceException;
import com.turing.code.common.mybatisplus.methods.CommonServiceImpl;
import com.turing.code.common.mybatisplus.structure.CustomQueryWrapper;
import com.turing.code.common.redis.utils.RedisUtil;
import com.turing.code.common.utils.returnbody.ReturnBody;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author turing generator
 * @since 2021-02-03
 */
@Service
public class AdRoleMenuServiceImpl extends CommonServiceImpl<AdRoleMenuMapper, AdRoleMenu> implements AdRoleMenuService {

    @Resource
    private RedisUtil redisUtil;

    @Transactional
    @Override
    public String setRoleMenu(Integer roleId, Integer[] menuIds) {
        // 查询该角色拥有权限的菜单
        QueryWrapper<AdRoleMenu> queryWrapper = new CustomQueryWrapper<>();
        queryWrapper.eq(AdRoleMenu.ROLE_ID, roleId);
        List<AdRoleMenu> list = super.list(queryWrapper);
        if (list.size() > 0) {
            // 删除该角色原有的菜单权限
            if (!super.remove(queryWrapper)) {
                throw new ServiceException("删除菜单权限失败");
            }
        }
        list = new ArrayList<>();
        AdRoleMenu adRoleMenu;
        // 新增菜单权限
        for (Integer menuId : menuIds) {
            adRoleMenu = new AdRoleMenu();
            adRoleMenu.setRoleId(roleId);
            adRoleMenu.setMenuId(menuId);
            list.add(adRoleMenu);
        }
        // 插入菜单权限
        if (!super.saveBatch(list)) {
            throw new ServiceException("新增菜单权限失败");
        }
        // 刷新权限
        redisUtil.hdel("roleMenu", roleId.toString());
        return ReturnBody.success();
    }


    @Override
    public List<AdMenu> selectRoleMenu(AdRoleMenu adminRoleMenu) {
        return super.baseMapper.selectRoleMenu(adminRoleMenu);
    }
}
