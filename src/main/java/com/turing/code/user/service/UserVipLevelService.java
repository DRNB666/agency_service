package com.turing.code.user.service;

import com.turing.code.tenant.pojo.TenantInfoFlowing;
import com.turing.code.user.pojo.*;
import com.turing.code.common.mybatisplus.methods.CommonService;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @author turing generator
 * @since 2024-06-23
 */
public interface UserVipLevelService extends CommonService<UserVipLevel> {

    BigDecimal highestDiscount(HashMap<String, Object> stringObjectHashMap);

    //开通会员礼包
    void openedVip(TenantInfoFlowing flowS, TenantInfoFlowing flowZ, UserVipFlow userVipFlow, UserInfo userInfo, UserOrder userOrder, UserVipLevel rawUserVip, UserOrderPay userOrderPay);
}
