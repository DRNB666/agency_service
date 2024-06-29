package com.turing.code.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.common.ex.ServiceException;
import com.turing.code.common.mybatisplus.methods.CommonServiceImpl;
import com.turing.code.tenant.pojo.TenantInfoFlowing;
import com.turing.code.tenant.service.TenantInfoFlowingService;
import com.turing.code.user.mapper.UserInfoMapper;
import com.turing.code.user.pojo.UserInfo;
import com.turing.code.user.pojo.UserVipFlow;
import com.turing.code.user.service.UserInfoService;
import com.turing.code.user.service.UserVipFlowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author turing generator
 * @since 2021-12-18
 */
@Service
public class UserInfoServiceImpl extends CommonServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserVipFlowService userVipFlowService;
    @Resource
    private TenantInfoFlowingService tenantInfoFlowingService;

    @Override
    public boolean isTeam(Long userId, Long agentId) {
        return isTeamLoop(userId, agentId);
    }

    private boolean isTeamLoop(Long userId, Long agentId) {
        QueryWrapper<UserInfo> eq = new QueryWrapper<UserInfo>().eq(UserInfo.INVITER_ID, userId);
        List<Long> ids = list(eq).stream().map(UserInfo::getId).collect(Collectors.toList());
        if (ids.isEmpty()) {
            return false;
        }
        for (int i = 0; i < ids.size(); i++) {
            Long id = ids.get(i);
            if (id.equals(agentId)) {
                return true;
            }
            if (isTeamLoop(id, agentId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer teamCount(Integer userId) {
        return teamCountLoop(userId, 1);
    }

    @Override
    public IPage<UserInfo> userList(Page<UserInfo> page, HashMap<String, Object> stringObjectHashMap) {
        return userInfoMapper.userList(page, stringObjectHashMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recharge(UserVipFlow userVipFlow, UserInfo userInfo, TenantInfoFlowing flowS, TenantInfoFlowing flowZ) {
        if (userInfoMapper.updateById(userInfo)!=1) {
            throw new ServiceException();
        }
        if (!userVipFlowService.save(userVipFlow)) {
            throw new ServiceException();
        }
        if (!tenantInfoFlowingService.save(flowS)) {
            throw new ServiceException();
        }
        if (!tenantInfoFlowingService.save(flowZ)) {
            throw new ServiceException();
        }
    }

    private Integer teamCountLoop(Integer inviterId, Integer count) {
        List<Integer> ids = userInfoMapper.selectByInviterId(inviterId);
        if (ids.isEmpty()) {
            return count;
        }
        count += ids.size();
        for (int i = 0; i < ids.size(); i++) {
            count = teamCountLoop(ids.get(i), count);
        }
        return count;
    }
}
