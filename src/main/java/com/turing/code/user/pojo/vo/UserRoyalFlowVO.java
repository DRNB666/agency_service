package com.turing.code.user.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserRoyalFlowVO {

    private Long id;

    private Long orderId;

    //佣金
    private BigDecimal secAmount;
    //订单总金额
    private BigDecimal orderAmount;

    private Integer type;

    private Integer status;

    private Date createTime;
    //昵称
    private String nick;
    //头像
    private String avatar;

    private Integer queryType;
    //1直推 2间推
    private Integer pushType;

    private String name;

    private String cityName;

    //来源id
    private Long sourceId;

}