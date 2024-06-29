package com.turing.code.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.admin.mapper.AdInfoMapper;
import com.turing.code.admin.pojo.AdInfo;
import com.turing.code.admin.pojo.AdMenu;
import com.turing.code.admin.pojo.AdRole;
import com.turing.code.admin.pojo.AdRoleMenu;
import com.turing.code.admin.pojo.vo.AdInfoVO;
import com.turing.code.admin.service.AdInfoService;
import com.turing.code.admin.service.AdMenuService;
import com.turing.code.admin.service.AdRoleMenuService;
import com.turing.code.admin.service.AdRoleService;
import com.turing.code.admin.tools.enums.AdMenuFlag;
import com.turing.code.common.config.KeyConfig;
import com.turing.code.common.ex.ServiceException;
import com.turing.code.common.mybatisplus.methods.CommonServiceImpl;
import com.turing.code.common.mybatisplus.structure.CustomQueryWrapper;
import com.turing.code.common.redis.utils.RedisUtil;
import com.turing.code.common.security.config.SecurityConfig;
import com.turing.code.common.security.jwt.JwtTokenUtil;
import com.turing.code.common.utils.returnbody.ResultCodeInfo;
import com.turing.code.common.utils.returnbody.ReturnBody;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author turing generator
 * @since 2021-02-03
 */
@Service
public class AdInfoServiceImpl extends CommonServiceImpl<AdInfoMapper, AdInfo> implements AdInfoService {

    @Resource
    private HttpServletRequest request;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private AdRoleMenuService adRoleMenuService;
    @Resource
    private AdMenuService adMenuService;
    @Resource
    private AdRoleService adRoleService;


    @Override
    public String authority(String account, String password) {
        // 获取管理员用户账号信息
        AdInfo adInfo = super.getOne(new CustomQueryWrapper<AdInfo>().eq(AdInfo.ACCOUNT, account));
        if (null == adInfo) {
            return ReturnBody.error("用户名或密码错误");
        }
        if (adInfo.getStatus() == -1) {
            return ReturnBody.error("该账号已被冻结, 请联系相关人员");
        }
        // 用户认证
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(account + SecurityConfig.ROLE_ADMIN, password + KeyConfig.KEY_PWD);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 更新登录时间
        adInfo.setLoginTime(new Date());
        if (!super.updateById(adInfo)) {
            return ReturnBody.error();
        }
        List<AdMenu> roleMenu = getRoleMenu();
        JSONObject jsonObject = new JSONObject();
        // 秘钥
        jsonObject.put("token", "Bearer_" + JwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal(), 7200));
        // 角色菜单
        jsonObject.put("menuList", roleMenu);
        return ReturnBody.success(jsonObject);
    }


    @Override
    public IPage<AdInfoVO> selectAndRole(Page page, QueryWrapper<AdInfoVO> queryWrapper) {
        return super.baseMapper.selectAndRole(page, queryWrapper);
    }


    @Transactional
    @Override
    public String add(AdInfo adInfo) {
        if (!super.save(adInfo)) {
            throw new ServiceException(ResultCodeInfo.ERROR.getResult());
        }
        AdRole adRole = adRoleService.getById(adInfo.getRoleId());
        adRole.setAccountNum(adRole.getAccountNum() + 1);
        if (!adRoleService.updateById(adRole)) {
            throw new ServiceException("更新角色账号数量失败");
        }
        return ReturnBody.success();
    }


    @Transactional
    @Override
    public String delete(Integer id) {
        AdInfo adInfo = super.getById(id);

        // 删除账号
        if (!super.removeById(id)) {
            throw new ServiceException(ResultCodeInfo.ERROR.getResult());
        }

        // 修改账号数量
        AdRole adRole = adRoleService.getById(adInfo.getRoleId());
        adRole.setAccountNum(adRole.getAccountNum() - 1);
        if (!adRoleService.updateById(adRole)) {
            throw new ServiceException("更新角色账号数量失败");
        }
        return ReturnBody.success();
    }

    @Transactional
    @Override
    public String updateInfo(AdInfo adInfo) {
        // 查询原信息
        AdInfo tempAdInfo = super.getById(adInfo.getId());

        // 判断是否修改角色
        if (!adInfo.getRoleId().equals(tempAdInfo.getRoleId())) {
            // 如果有修改角色 则变更角色下的 账号数量
            AdRole adRole = adRoleService.getById(tempAdInfo.getRoleId());
            adRole.setAccountNum(adRole.getAccountNum() - 1);
            if (!adRoleService.updateById(adRole)) {
                throw new ServiceException("变更角色失败");
            }

            adRole = adRoleService.getById(adInfo.getRoleId());
            adRole.setAccountNum(adRole.getAccountNum() + 1);
            if (!adRoleService.updateById(adRole)) {
                throw new ServiceException("变更角色失败");
            }
        }

        // 更新账号
        if (!super.updateById(adInfo)) {
            throw new ServiceException("更新账号失败");
        }
        return ReturnBody.success();
    }


    // 获取该角色拥有权限的菜单
    private List<AdMenu> getRoleMenu() {
        // 获取该角色拥有权限的菜单
        Integer roleId = (Integer) request.getAttribute(AdInfo.ROLE_ID);
        AdRoleMenu adminRoleMenu = new AdRoleMenu();
        List<AdMenu> list;
        if (roleId != 1) {
            adminRoleMenu.setRoleId(roleId);
            list = adRoleMenuService.selectRoleMenu(adminRoleMenu);
        } else {
            QueryWrapper<AdMenu> queryWrapper = new CustomQueryWrapper<AdMenu>().orderByDesc(AdMenu.SORT);
            list = adMenuService.list(queryWrapper);
        }

        if (list.size() > 0) {
            redisUtil.hset("roleMenu", roleId.toString(), list);
        } else {
            throw new ServiceException("获取菜单列表失败");
        }
        return list.stream().filter(item -> item.getFlag().equals(AdMenuFlag.MENU.getValue()) || item.getFlag().equals(AdMenuFlag.COMM_PAGE.getValue())).collect(Collectors.toList());
    }
}
