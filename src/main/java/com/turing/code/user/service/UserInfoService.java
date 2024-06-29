package com.turing.code.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.common.mybatisplus.methods.CommonService;
import com.turing.code.tenant.pojo.TenantInfoFlowing;
import com.turing.code.user.pojo.UserInfo;
import com.turing.code.user.pojo.UserVipFlow;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author turing generator
 * @since 2021-12-18
 */
public interface UserInfoService extends CommonService<UserInfo> {
    /**
     * 判断agentId是否在userId的团队里
     * @param userId
     * @param agentId
     * @return
     */
    boolean isTeam(Long userId, Long agentId);
    /**
     * 团队人数(所有下级)
     * @param userId
     * @return
     */
    Integer teamCount(Integer userId);


    /**
     * 管理端用户列表
     */
    IPage<UserInfo> userList(Page<UserInfo> page, HashMap<String, Object> stringObjectHashMap);

    /**
     * 租户充值用户储蓄卡余额
     */
    void recharge(UserVipFlow userVipFlow, UserInfo userInfo, TenantInfoFlowing flowS, TenantInfoFlowing flowZ);
}
