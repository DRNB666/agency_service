package com.leepsmart.code.common.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leepsmart.code.admin.pojo.AdInfo;
import com.leepsmart.code.admin.service.AdInfoService;
import com.leepsmart.code.common.config.KeyConfig;
import com.leepsmart.code.common.mybatisplus.structure.CustomQueryWrapper;
import com.leepsmart.code.common.security.config.SecurityConfig;
import com.leepsmart.code.common.security.jwt.JwtUser;
import com.leepsmart.code.user.mapper.UserInfoMapper;
import com.leepsmart.code.user.pojo.UserInfo;
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
 * @author leepsmart
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



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.endsWith(SecurityConfig.ROLE_USER)) { //微信用户认证
            UserInfo user = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq(UserInfo.ACCOUNT,
                    username.replace(SecurityConfig.ROLE_USER, "")));
            if (user == null) {
                throw new UsernameNotFoundException(String.format("'%s' invalid", username));
            } else if (-1 == user.getStatus()) {
                throw new LockedException("账户已被冻结");
            }else if(user.getLoginStatus()!=1){
                throw new LockedException("账户状态异常");
            }
            request.setAttribute("id", user.getId());
            request.setAttribute("role", user.getRoleId());
            return new JwtUser(username,user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(SecurityConfig.ROLE_USER));
        } else if (username.endsWith(SecurityConfig.ROLE_ADMIN)) {
            QueryWrapper<AdInfo> queryWrapper = new CustomQueryWrapper<AdInfo>()
                    .eq(AdInfo.ACCOUNT, username.replace(SecurityConfig.ROLE_ADMIN, ""));
            AdInfo user = adInfoService.getOne(queryWrapper);
            if (user == null) {
                throw new UsernameNotFoundException(String.format("'%s' invalid", username));
            } else {
                request.setAttribute("adId", user.getId());
                request.setAttribute("adRoleId", user.getRoleId());
                return new JwtUser(username, user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(SecurityConfig.ROLE_ADMIN));
            }
        } else {
            throw new UsernameNotFoundException(String.format("'%s' invalid", username));
        }
    }

}
