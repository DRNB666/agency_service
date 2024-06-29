package com.turing.code.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.tenant.pojo.vo.TenantOrderVo;
import com.turing.code.user.pojo.UserOrderPay;
import com.turing.code.common.mybatisplus.methods.CommonService;

import java.util.HashMap;

/**
 * @author turing generator
 * @since 2024-06-05
 */
public interface UserOrderPayService extends CommonService<UserOrderPay> {

    /**
     * 订单列表
     */
    IPage<TenantOrderVo> orderList(Page<TenantOrderVo> page, HashMap<String, Object> stringObjectHashMap);
}
