package com.turing.code.tenant.pojo.vo;

import com.turing.code.user.pojo.UserOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TenantOrderVo extends UserOrder {
    /**
     * 实际支付
     */
    private BigDecimal actuallyPay;
    /**
     * 会员折扣
     */
    private BigDecimal vipSale;
    /**
     * 用户信息
     */
    private String userName;
    /**
     * 所有商品发货状态1：未完成 2：已完成
     */
    private Integer devStatus;

}
