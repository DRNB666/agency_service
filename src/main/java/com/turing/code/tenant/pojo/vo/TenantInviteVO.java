package com.turing.code.tenant.pojo.vo;

import com.turing.code.tenant.pojo.TenantInvite;
import com.turing.code.user.pojo.UserInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TenantInviteVO extends TenantInvite {

    private List<UserInfo> userInfoList;
    //上级昵称
    private String agentNick;
    //累计用户
    private Integer inviteUser;
    //累计下级
    private Integer inviteAgent;
    //团队业绩
    private BigDecimal teamAmount;

    //今日开单数
    private Integer orderCount;
    //本月业绩
    private BigDecimal monthAmount;
    //总业绩
    private BigDecimal totalAmount;


}