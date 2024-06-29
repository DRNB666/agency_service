package com.turing.code.tenant.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.code.admin.pojo.AdInfo;
import com.turing.code.admin.pojo.AdMenu;
import com.turing.code.admin.service.AdMenuService;
import com.turing.code.admin.service.AdRoleMenuService;
import com.turing.code.admin.service.AdRoleService;
import com.turing.code.admin.tools.enums.AdMenuFlag;
import com.turing.code.common.config.KeyConfig;
import com.turing.code.common.ex.ServiceException;
import com.turing.code.common.mybatisplus.structure.CustomQueryWrapper;
import com.turing.code.common.redis.utils.RedisUtil;
import com.turing.code.common.security.config.SecurityConfig;
import com.turing.code.common.security.jwt.JwtTokenUtil;
import com.turing.code.common.utils.CommUtil;
import com.turing.code.common.utils.returnbody.ReturnBody;
import com.turing.code.tenant.pojo.TenantInfo;
import com.turing.code.tenant.mapper.TenantInfoMapper;
import com.turing.code.tenant.pojo.TenantMenu;
import com.turing.code.tenant.pojo.TenantRoleMenu;
import com.turing.code.tenant.service.TenantInfoService;
import com.turing.code.common.mybatisplus.methods.CommonServiceImpl;
import com.turing.code.tenant.service.TenantMenuService;
import com.turing.code.tenant.service.TenantRoleMenuService;
import com.turing.code.tenant.service.TenantRoleService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author turing generator
 * @since 2024-05-27
 */
@Service
public class TenantInfoServiceImpl extends CommonServiceImpl<TenantInfoMapper, TenantInfo> implements TenantInfoService {

    @Resource
    private HttpServletRequest request;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private TenantRoleMenuService tenantRoleMenuService;
    @Resource
    private TenantMenuService tenantMenuService;
    @Resource
    private TenantRoleService tenantRoleService;


    @Override
    public String authority(String account, String password) {
        // 获取管理员用户账号信息
        TenantInfo tenantInfo = super.getOne(new CustomQueryWrapper<TenantInfo>().eq(TenantInfo.ACCOUNT, account));
        if (null == tenantInfo) {
            return ReturnBody.error("用户名或密码错误");
        }
        if (tenantInfo.getStatus() == -1) {
            return ReturnBody.error("该账号已被冻结, 请联系相关人员");
        }
        // 用户认证
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(account + SecurityConfig.ROLE_TENANT,
                password + KeyConfig.KEY_PWD);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 更新登录时间
        if (!super.updateById(tenantInfo)) {
            return ReturnBody.error();
        }
        List<TenantMenu> roleMenu = getRoleMenu();
        JSONObject jsonObject = new JSONObject();
        // 秘钥
        jsonObject.put("token", "Bearer_" + JwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal(), 7200));
        // 角色菜单
        jsonObject.put("menuList", roleMenu);
        jsonObject.put("name", CommUtil.checkNull(tenantInfo.getName())?"："+tenantInfo.getName():"");
        return ReturnBody.success(jsonObject);
    }
    // 获取该角色拥有权限的菜单
    private List<TenantMenu> getRoleMenu(){
        // 获取该角色拥有权限的菜单
        Integer roleId = (Integer)request.getAttribute(TenantInfo.ROLE_ID);
        TenantRoleMenu tenantRoleMenu = new TenantRoleMenu();
        List<TenantMenu> list;
        if (roleId != 1){
            tenantRoleMenu.setRoleId(roleId);
            list = tenantRoleMenuService.selectRoleMenu(tenantRoleMenu);
        }else{
            QueryWrapper<TenantMenu> queryWrapper = new QueryWrapper<TenantMenu>().orderByDesc(TenantMenu.SORT);
            list = tenantMenuService.list(queryWrapper);
        }

        if (list.size() > 0){
            redisUtil.hset("roleMenu", roleId.toString(), list);
        }else{
            throw new ServiceException("获取菜单列表失败");
        }
        return list.stream().filter(item-> item.getFlag().equals(AdMenuFlag.MENU.getValue()) || item.getFlag().equals(AdMenuFlag.COMM_PAGE.getValue())).collect(Collectors.toList());
    }
}
