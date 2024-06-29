package com.turing.code.tenant.service.impl;

import com.turing.code.tenant.pojo.TenantOrderDelivery;
import com.turing.code.tenant.mapper.TenantOrderDeliveryMapper;
import com.turing.code.tenant.pojo.vo.TenantOrderDevVo;
import com.turing.code.tenant.service.TenantOrderDeliveryService;
import com.turing.code.common.mybatisplus.methods.CommonServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author turing generator
 * @since 2024-06-27
 */
@Service
public class TenantOrderDeliveryServiceImpl extends CommonServiceImpl<TenantOrderDeliveryMapper, TenantOrderDelivery> implements TenantOrderDeliveryService {

    @Resource
    private TenantOrderDeliveryMapper tenantOrderDeliveryMapper;

    @Override
    public List<TenantOrderDevVo> devList(Long orderId) {
        return tenantOrderDeliveryMapper.devList(orderId);
    }
}
