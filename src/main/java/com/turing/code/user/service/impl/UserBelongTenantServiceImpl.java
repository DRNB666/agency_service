package com.turing.code.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.tenant.pojo.vo.TenantUserVo;
import com.turing.code.user.pojo.UserBelongTenant;
import com.turing.code.user.mapper.UserBelongTenantMapper;
import com.turing.code.user.service.UserBelongTenantService;
import com.turing.code.common.mybatisplus.methods.CommonServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author turing generator
 * @since 2024-06-18
 */
@Service
public class UserBelongTenantServiceImpl extends CommonServiceImpl<UserBelongTenantMapper, UserBelongTenant> implements UserBelongTenantService {

    @Resource
    private UserBelongTenantMapper userBelongTenantMapper;

    @Override
    public IPage<TenantUserVo> userList(Page<TenantUserVo> page, HashMap<String, Object> stringObjectHashMap) {
        return userBelongTenantMapper.userList(page,stringObjectHashMap);
    }
}
