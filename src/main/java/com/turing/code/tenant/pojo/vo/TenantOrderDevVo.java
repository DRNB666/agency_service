package com.turing.code.tenant.pojo.vo;

import com.turing.code.tenant.pojo.TenantOrderDelivery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TenantOrderDevVo extends TenantOrderDelivery {
    private String tenantName;
}
