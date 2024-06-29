package com.turing.code.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.code.common.ex.ServiceException;
import com.turing.code.common.redis.utils.RedisUtil;
import com.turing.code.common.utils.returnbody.ReturnBody;
import com.turing.code.tenant.pojo.TenantMenu;
import com.turing.code.tenant.pojo.TenantRoleMenu;
import com.turing.code.tenant.mapper.TenantRoleMenuMapper;
import com.turing.code.tenant.service.TenantRoleMenuService;
import com.turing.code.common.mybatisplus.methods.CommonServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author turing generator
 * @since 2024-05-28
 */
@Service
public class TenantRoleMenuServiceImpl extends CommonServiceImpl<TenantRoleMenuMapper, TenantRoleMenu> implements TenantRoleMenuService {
    @Resource
    private RedisUtil redisUtil;

    @Override
    public List<TenantMenu> selectRoleMenu(TenantRoleMenu tenantRoleMenu) {
        return super.baseMapper.selectRoleMenu(tenantRoleMenu);
    }

    @Override
    public String setRoleMenu(Integer roleId, Integer[] menuIds) {
        // 查询该角色拥有权限的菜单
        QueryWrapper<TenantRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(TenantRoleMenu.ROLE_ID, roleId);
        List<TenantRoleMenu> list = super.list(queryWrapper);
        if (list.size() > 0){
            // 删除该角色原有的菜单权限
            if (!super.remove(queryWrapper)) {
                throw new ServiceException("删除菜单权限失败");
            }
        }
        list = new ArrayList<>();
        TenantRoleMenu adRoleMenu;
        // 新增菜单权限
        for (Integer menuId : menuIds) {
            adRoleMenu = new TenantRoleMenu();
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
}
