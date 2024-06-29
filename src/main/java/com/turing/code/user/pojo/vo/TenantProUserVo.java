package com.turing.code.user.pojo.vo;

import com.turing.code.tenant.pojo.TenantPro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TenantProUserVo extends TenantPro {
    private Integer vipSale;
    private BigDecimal salePrice;

}
