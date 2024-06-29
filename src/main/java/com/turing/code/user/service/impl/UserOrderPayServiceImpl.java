package com.turing.code.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.tenant.pojo.vo.TenantOrderVo;
import com.turing.code.user.pojo.UserOrderPay;
import com.turing.code.user.mapper.UserOrderPayMapper;
import com.turing.code.user.service.UserOrderPayService;
import com.turing.code.common.mybatisplus.methods.CommonServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author turing generator
 * @since 2024-06-05
 */
@Service
public class UserOrderPayServiceImpl extends CommonServiceImpl<UserOrderPayMapper, UserOrderPay> implements UserOrderPayService {

    @Resource
    private UserOrderPayMapper userOrderPayMapper;

    @Override
    public IPage<TenantOrderVo> orderList(Page<TenantOrderVo> page, HashMap<String, Object> stringObjectHashMap) {
        return userOrderPayMapper.orderList(page,stringObjectHashMap);
    }
}
