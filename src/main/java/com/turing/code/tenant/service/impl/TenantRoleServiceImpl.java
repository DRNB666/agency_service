package com.turing.code.tenant.service.impl;

import com.turing.code.tenant.pojo.TenantInfo;
import com.turing.code.tenant.pojo.TenantRole;
import com.turing.code.tenant.mapper.TenantRoleMapper;
import com.turing.code.tenant.service.TenantInfoService;
import com.turing.code.tenant.service.TenantRoleService;
import com.turing.code.common.mybatisplus.methods.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author turing generator
 * @since 2024-05-28
 */
@Service
public class TenantRoleServiceImpl extends CommonServiceImpl<TenantRoleMapper, TenantRole> implements TenantRoleService {



}
