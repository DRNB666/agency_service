package com.turing.code.user.pojo.vo;

import com.turing.code.tenant.pojo.TenantProSku;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TenantProSkuVo extends TenantProSku {
    private BigDecimal vipPrice;
}
