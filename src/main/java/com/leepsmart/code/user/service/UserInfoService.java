package com.leepsmart.code.user.service;


import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leepsmart.code.common.mybatisplus.methods.CommonService;
import com.leepsmart.code.user.pojo.UserInfo;

import java.util.HashMap;

/**
 * @author leepsmart generator
 * @since 2021-12-18
 */
public interface UserInfoService extends CommonService<UserInfo> {


    /**
     * 管理端用户列表
     */
    IPage<UserInfo> userList(Page<UserInfo> page, HashMap<String, Object> stringObjectHashMap);


    //用户端-注册或登录
    JSONObject registerOrLogin(String email);
}
