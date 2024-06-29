package com.turing.code.user.service;

import com.turing.code.tenant.pojo.TenantInvite;
import com.turing.code.user.pojo.UserOrder;
import com.turing.code.user.pojo.vo.MerchantProInvite;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserMerchantInviteService {

    /**
     * 处理分销员，分销商品
     */
    void addInviteFlow( UserOrder userOrder);

    /**
     * 查询用户的分销店铺列表
     * @param map
     * @return
     */
    List<Map<String, Object>> selectUserInviteShop(Map<String, Object> map);


    List<MerchantProInvite> selectMerchantProInviteList(Map<String, Object> map);

    BigDecimal selectSumProAmountByAgentId(Long agentId, Long shopId);

    TenantInvite selectOneByMap(Map<String, Object> map);

}