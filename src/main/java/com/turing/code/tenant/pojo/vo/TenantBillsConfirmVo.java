package com.turing.code.tenant.pojo.vo;

import com.turing.code.common.utils.QrCodeUtil;
import com.turing.code.tenant.pojo.TenantOrderBills;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TenantBillsConfirmVo extends TenantOrderBills {
    /**
     * 用户信息
     */
    private String userName;
    /**
     * 商品数量
     */
    private Integer count;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 实际支付
     */
    private BigDecimal payAmount;
    /**
     * 登记人
     */
    private String registerAd;
    /**
     * 审核人
     */
    private String auditAd;
}
