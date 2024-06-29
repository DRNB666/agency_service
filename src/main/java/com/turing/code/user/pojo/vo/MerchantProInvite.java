package com.turing.code.user.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MerchantProInvite {

    private Long id;

    private Long tenantId;

    private Long shopId;

    private Integer categoryId;

    private String name;

    private String cover;

    private BigDecimal price;

    private BigDecimal originalPrice;

    private Integer status;

    private Integer isInvite;

    private Integer isSpec;

    private Date createTime;

    /** 1普通 2秒杀 3团购*/
    private Integer type;
    //最大佣金
    private BigDecimal maxReward;

}