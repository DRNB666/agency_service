package com.turing.code.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.code.common.ex.ServiceException;
import com.turing.code.common.utils.CommUtil;
import com.turing.code.tenant.mapper.TenantInfoFlowingMapper;
import com.turing.code.tenant.pojo.TenantInfoFlowing;
import com.turing.code.user.mapper.*;
import com.turing.code.user.pojo.*;
import com.turing.code.user.service.UserVipLevelService;
import com.turing.code.common.mybatisplus.methods.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @author turing generator
 * @since 2024-06-23
 */
@Service
public class UserVipLevelServiceImpl extends CommonServiceImpl<UserVipLevelMapper, UserVipLevel> implements UserVipLevelService {

    @Resource
    private UserVipLevelMapper userVipLevelMapper;
    @Resource
    private TenantInfoFlowingMapper tenantInfoFlowingMapper;
    @Resource
    private UserVipFlowMapper userVipFlowMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserOrderMapper userOrderMapper;
    @Resource
    private UserOrderPayMapper userOrderPayMapper;

    @Override
    public BigDecimal highestDiscount(HashMap<String, Object> stringObjectHashMap) {
        return userVipLevelMapper.highestDiscount(stringObjectHashMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void openedVip(TenantInfoFlowing flowS, TenantInfoFlowing flowZ, UserVipFlow userVipFlow, UserInfo userInfo, UserOrder userOrder, UserVipLevel rawUserVip, UserOrderPay userOrderPay) {
        //商家流水
        if (CommUtil.checkNull(flowS)) {
            if (tenantInfoFlowingMapper.insert(flowS) != 1) {
                throw new ServiceException();
            }
        }
        if (CommUtil.checkNull(flowZ)) {
            if (tenantInfoFlowingMapper.insert(flowZ) != 1) {
                throw new ServiceException();
            }
        }
        //用户储蓄流水
        if (CommUtil.checkNull(userVipFlow)) {
            if (userVipFlowMapper.insert(userVipFlow) != 1) {
                throw new ServiceException();
            }
        }
        //会员礼包等级
        if (CommUtil.checkNull(rawUserVip)) {
            //删除之前的会员等级，如果有的话
            QueryWrapper<UserVipLevel> eq = new QueryWrapper<UserVipLevel>().eq(UserVipLevel.TENANT_ID, rawUserVip.getTenantId())
                    .eq(UserVipLevel.US_ID, rawUserVip.getUsId());
            userVipLevelMapper.delete(eq);
            //保存新会员等级
            if (userVipLevelMapper.insert(rawUserVip)!=1) {
                throw new ServiceException();
            }
        }
        //用户订单
        if (userOrderMapper.insert(userOrder) != 1) {
            throw new ServiceException();
        }
        if (userOrderPayMapper.insert(userOrderPay)!=1) {
            throw new ServiceException();
        }
        //修改用户信息
        if (userInfoMapper.updateById(userInfo) != 1) {
            throw new ServiceException();
        }
    }
}
