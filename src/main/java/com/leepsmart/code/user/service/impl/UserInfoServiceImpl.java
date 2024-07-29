package com.leepsmart.code.user.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leepsmart.code.common.config.KeyConfig;
import com.leepsmart.code.common.ex.ServiceException;
import com.leepsmart.code.common.mybatisplus.methods.CommonServiceImpl;

import com.leepsmart.code.common.security.config.SecurityConfig;
import com.leepsmart.code.common.security.jwt.JwtTokenUtil;
import com.leepsmart.code.common.utils.CommUtil;
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
import java.util.Date;
import java.util.HashMap;

/**
 * @author leepsmart generator
 * @since 2021-12-18
 */
@Service
public class UserInfoServiceImpl extends CommonServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private BCryptPasswordEncoder encoder;


    @Override
    public IPage<UserInfo> userList(Page<UserInfo> page, HashMap<String, Object> stringObjectHashMap) {
        return userInfoMapper.userList(page, stringObjectHashMap);
    }

    @Override
    public JSONObject registerOrLogin(String email) {
        JSONObject result = new JSONObject();
        //判断是否是新用户
        QueryWrapper<UserInfo> eq = new QueryWrapper<>();
        eq.eq(UserInfo.EMAIL, email);
        UserInfo userInfo = getOne(eq);
        if (CommUtil.checkNull(userInfo)){
            //获取token
            getToken(userInfo.getAccount(), result, userInfo);
        }else {
            String account="";
            UserInfo newUser = new UserInfo().setEmail(email).setLoginTime(new Date())
                    .setPassword((encoder.encode("123456" + KeyConfig.KEY_PWD))).setUpdateTime(new Date());
            while (true){
                String pre="AGENCY-";
                String acNumber = CommUtil.createRandom(false, 6);
                account=pre+acNumber;
                QueryWrapper<UserInfo> accountUserEq = new QueryWrapper<UserInfo>().eq(UserInfo.ACCOUNT, account);
                UserInfo rawUser = getOne(accountUserEq);
                if (!CommUtil.checkNull(rawUser)){
                    newUser.setAccount(account);
                    break;
                }
            }
            if (!save(newUser)) {
                throw new ServiceException("注册账号失败");
            }
            getToken(account, result, newUser);
        }
        return result;
    }
    private void getToken(String account, JSONObject result, UserInfo userInfo) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(userInfo.getAccount() + SecurityConfig.ROLE_USER,
                "123456"+ KeyConfig.KEY_PWD);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(account + SecurityConfig.ROLE_USER);
        result.put("token", "Bearer_" + JwtTokenUtil.generateToken(userDetails));
        //暂时写死返回
        result.put("menuList", "[{\"component\":\"\",\"createTime\":\"2019-11-04 16:54:37\",\"flag\":1,\"id\":9,\"menuName\":\"媒体管理\",\"parentId\":-1,\"requestUrl\":\"\",\"sort\":0,\"updateTime\":\"2024-07-25 17:30:31\",\"version\":1},{\"component\":\"Media\",\"createTime\":\"2019-11-04 16:54:37\",\"flag\":1,\"id\":10,\"menuName\":\"媒体管理\",\"parentId\":9,\"requestUrl\":\"/Media\",\"sort\":0,\"updateTime\":\"2024-07-25 17:30:31\",\"version\":1}]");
    }

}
