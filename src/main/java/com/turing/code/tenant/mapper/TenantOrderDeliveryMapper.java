package com.turing.code.tenant.mapper;

import com.turing.code.tenant.pojo.TenantOrderDelivery;
import com.turing.code.common.mybatisplus.methods.CommonMapper;
import com.turing.code.tenant.pojo.vo.TenantOrderDevVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author turing generator
 * @since 2024-06-27
 */
public interface TenantOrderDeliveryMapper extends CommonMapper<TenantOrderDelivery> {

    List<TenantOrderDevVo> devList(@Param("orderId") Long orderId);
}
