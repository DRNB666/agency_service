package com.turing.code.common.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.code.admin.pojo.AdInfo;
import com.turing.code.admin.service.AdInfoService;
import com.turing.code.common.config.KeyConfig;
import com.turing.code.common.mybatisplus.structure.CustomQueryWrapper;
import com.turing.code.common.security.config.SecurityConfig;
import com.turing.code.common.security.jwt.JwtUser;
import com.turing.code.tenant.pojo.TenantInfo;
import com.turing.code.tenant.service.TenantInfoService;
import com.turing.code.user.mapper.UserInfoMapper;
import com.turing.code.user.pojo.UserInfo;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户验证方法
 *
 * @author Turing
 * Created on 2017/12/8
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private HttpServletRequest request;
    @Resource
    private AdInfoService adInfoService;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private BCryptPasswordEncoder encoder;
    @Resource
    private TenantInfoService tenantInfoService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.endsWith(SecurityConfig.ROLE_USER)) { //微信用户认证
            UserInfo user = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq(UserInfo.OPEN_ID, username.replace(SecurityConfig.ROLE_USER, "")));
            if (user == null) {
                throw new UsernameNotFoundException(String.format("'%s' invalid", username));
            } else if (-1 == user.getStatus()) {
                throw new LockedException("账户已被冻结");
            }
            request.setAttribute("info", user);
            request.setAttribute("id", user.getId());
            return new JwtUser(username,user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(SecurityConfig.ROLE_USER));

        } else if (username.endsWith(SecurityConfig.ROLE_ADMIN)) {
            QueryWrapper<AdInfo> queryWrapper = new CustomQueryWrapper<AdInfo>()
                    .eq(AdInfo.ACCOUNT, username.replace(SecurityConfig.ROLE_ADMIN, ""));
            AdInfo user = adInfoService.getOne(queryWrapper);
            if (user == null) {
                throw new UsernameNotFoundException(String.format("'%s' invalid", username));
            } else {
                request.setAttribute(AdInfo.ID, user.getId());
                request.setAttribute(AdInfo.ROLE_ID, user.getRoleId());
                return new JwtUser(username, user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(SecurityConfig.ROLE_ADMIN));
            }
        }else if (username.endsWith(SecurityConfig.ROLE_TENANT)) {
            QueryWrapper<TenantInfo> queryWrapper = new CustomQueryWrapper<TenantInfo>()
                    .eq(TenantInfo.ACCOUNT, username.replace(SecurityConfig.ROLE_TENANT, ""));
            TenantInfo user = tenantInfoService.getOne(queryWrapper);
            if (user == null) {
                throw new UsernameNotFoundException(String.format("'%s' invalid", username));
            } else {
                request.setAttribute(TenantInfo.ID, user.getId());
                request.setAttribute(TenantInfo.ROLE_ID, user.getRoleId());
                request.setAttribute("pId", user.getParentId()==-1?user.getId():user.getParentId());
                request.setAttribute("info", user);
                return new JwtUser(username, user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(SecurityConfig.ROLE_TENANT));
            }
        } else {
            throw new UsernameNotFoundException(String.format("'%s' invalid", username));
        }
    }

}
