package com.turing.code.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.tenant.pojo.vo.TenantOrderVo;
import com.turing.code.user.pojo.UserOrderPay;
import com.turing.code.common.mybatisplus.methods.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * @author turing generator
 * @since 2024-06-05
 */
public interface UserOrderPayMapper extends CommonMapper<UserOrderPay> {

    IPage<TenantOrderVo> orderList(Page<TenantOrderVo> page, @Param("param") HashMap<String, Object> stringObjectHashMap);
}
