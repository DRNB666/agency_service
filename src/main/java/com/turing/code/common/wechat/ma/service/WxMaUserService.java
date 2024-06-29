package com.turing.code.common.wechat.ma.service;

import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.code.common.config.KeyConfig;
import com.turing.code.common.ex.ServiceException;
import com.turing.code.common.security.config.SecurityConfig;
import com.turing.code.common.security.jwt.JwtTokenUtil;
import com.turing.code.common.utils.CommUtil;
import com.turing.code.common.utils.encrypt.RSAUtil;
import com.turing.code.common.utils.returnbody.ReturnBody;
import com.turing.code.user.mapper.UserInfoMapper;
import com.turing.code.user.pojo.UserBelongTenant;
import com.turing.code.user.pojo.UserInfo;
import com.turing.code.user.service.UserBelongTenantService;
import com.turing.code.user.service.UserInfoService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("ALL")
@Service
public class WxMaUserService {

    @Resource
    HttpServletRequest request;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private BCryptPasswordEncoder encoder;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserBelongTenantService userBelongTenantService;

    /**
     * 登录
     *
     * @param sessionKey
     * @param openId
     * @param parentId
     * @return
     * @throws Exception
     */
    public Map<String, Object> login(String sessionKey, String openId, Integer parentId) {
        Map<String, Object> map = new HashMap<>(10);
        if (!CommUtil.checkNull(sessionKey, openId)) {
            throw new ServiceException("授权失败");
        }
        // 生成加密的sessionKey
        try {
            map.put("encryption", RSAUtil.encryptByPublicKey(sessionKey, RSAUtil.PUBLIC_KEY));
        } catch (Exception e) {
            throw new ServiceException("授权失败");
        }
        UserInfo user = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq(UserInfo.OPEN_ID, openId));
        if (null == user) {
            // 新客户，注册账号
            user=addUser(openId);
            map.put("newUser","1");
        }else {
            map.put("newUser","0");
        }
        //获取token
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(openId + SecurityConfig.ROLE_USER,
                "123456"+KeyConfig.KEY_PWD);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(openId + SecurityConfig.ROLE_USER);
        map.put("token", "Bearer_" + JwtTokenUtil.generateToken(userDetails));
        map.put("openId", openId);
        map.put("userId", user.getId());
        return map;
    }


    /**
     * 授权
     *
     * @return
     */
    public String author(WxMaUserInfo wxMaUserInfo) {
        UserInfo userInfo = (UserInfo) request.getAttribute("info");
        userInfo.setNick(wxMaUserInfo.getNickName());
        userInfo.setAvatar(wxMaUserInfo.getAvatarUrl());
        if (userInfoMapper.updateById(userInfo) != 1) {
            return ReturnBody.error("授权失败");
        }

        return ReturnBody.success(userInfo);
    }

    /**
     * 解析电话号码
     *
     * @return
     * @deprecated 已过期
     */
    public String parsePhone(WxMaPhoneNumberInfo phoneNumberInfo) {
        UserInfo userInfo = (UserInfo) request.getAttribute("info");
        if (userInfoMapper.updateById(userInfo) != 1) {
            return ReturnBody.error("解析错误");
        }
/*        Map<String, Object> map = new HashMap<>();
        UserDetails userDetails = userDetailsService.loadUserByUsername(userInfo.getOpenId() + SecurityConfig.ROLE_USER);
        map.put("token", "Bearer_" + JwtTokenUtil.generateToken(userDetails));
        map.put("phone", userInfo.getPhone());*/
        return ReturnBody.success();
    }

    /**
     * 解析存储电话号码
     */
    public void savePhone(UserInfo info, WxMaPhoneNumberInfo phoneNoInfo) {
        if (!CommUtil.checkNull(phoneNoInfo) || !CommUtil.checkNull(phoneNoInfo.getPhoneNumber())) {
            throw new ServiceException("解析手机号码失败");
        }
        info.setMobile(phoneNoInfo.getPhoneNumber());
        if (userInfoMapper.updateById(info) != 1) {
            throw new ServiceException();
        }

    }

    private UserInfo addUser(String openId) {
        UserInfo userInfo = new UserInfo();
        //固定8位邀请码，数字英文混写
        while (true) {
            String code = UUID.randomUUID().toString().split("-")[0].toUpperCase();
            QueryWrapper<UserInfo> eq = new QueryWrapper<UserInfo>().eq(UserInfo.INVITER_CODE, code);
            UserInfo rawUser = userInfoMapper.selectOne(eq);
            if (!CommUtil.checkNull(rawUser)) {
                userInfo.setInviterCode(code);
                break;
            }
        }
        userInfo.setAvatar("/files/default/default_avatar.jfif");
        userInfo.setNick("用户" + RandomUtil.randomString(8));
        userInfo.setOpenId(openId);
        userInfo.setPassword(encoder.encode("123456" + KeyConfig.KEY_PWD));
        if (userInfoMapper.insert(userInfo) != 1) {
            return null;
        }
        return userInfo;
    }
}
