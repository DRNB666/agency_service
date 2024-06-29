package com.turing.code.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.tenant.pojo.vo.TenantUserVo;
import com.turing.code.user.pojo.UserBelongTenant;
import com.turing.code.common.mybatisplus.methods.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * @author turing generator
 * @since 2024-06-18
 */
public interface UserBelongTenantMapper extends CommonMapper<UserBelongTenant> {

    IPage<TenantUserVo> userList(Page<TenantUserVo> page, @Param("param") HashMap<String, Object> stringObjectHashMap);
}
