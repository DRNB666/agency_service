package com.turing.code.tenant.service;

import com.turing.code.tenant.pojo.TenantOrderDelivery;
import com.turing.code.common.mybatisplus.methods.CommonService;
import com.turing.code.tenant.pojo.vo.TenantOrderDevVo;

import java.util.List;

/**
 * @author turing generator
 * @since 2024-06-27
 */
public interface TenantOrderDeliveryService extends CommonService<TenantOrderDelivery> {

    /**
     * 发货信息列表
     */
    List<TenantOrderDevVo> devList(Long orderId);
}
