package com.turing.code.admin.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AdFlowVO {

    private Long id;

    private Integer type;

    private BigDecimal money;

    private String name;

    private String remark;

    private Date createTime;

    private Long orderId;

    private BigDecimal orderMoney;

    private Integer status;

}