package com.leepsmart.code.common.wechat.ma.service;

import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leepsmart.code.common.config.KeyConfig;
import com.leepsmart.code.common.ex.ServiceException;
import com.leepsmart.code.common.security.config.SecurityConfig;
import com.leepsmart.code.common.security.jwt.JwtTokenUtil;
import com.leepsmart.code.common.utils.CommUtil;
import com.leepsmart.code.common.utils.encrypt.RSAUtil;
import com.leepsmart.code.common.utils.returnbody.ReturnBody;
import com.leepsmart.code.user.mapper.UserInfoMapper;
import com.leepsmart.code.user.pojo.UserInfo;
import com.leepsmart.code.user.service.UserInfoService;
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
//            user=addUser(openId);
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

}
