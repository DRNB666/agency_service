package com.turing.code.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.code.common.ex.ServiceException;
import com.turing.code.common.utils.CommUtil;
import com.turing.code.tenant.mapper.TenantInviteConfigMapper;
import com.turing.code.tenant.mapper.TenantInviteMapper;
import com.turing.code.tenant.mapper.TenantProMapper;
import com.turing.code.tenant.pojo.TenantInvite;
import com.turing.code.tenant.pojo.TenantInviteConfig;
import com.turing.code.tenant.pojo.TenantPro;
import com.turing.code.user.mapper.UserInfoMapper;
import com.turing.code.user.mapper.UserRoyalFlowProMapper;
import com.turing.code.user.pojo.UserInfo;
import com.turing.code.user.pojo.UserOrder;
import com.turing.code.user.pojo.UserOrderPay;
import com.turing.code.user.pojo.UserRoyalFlowPro;
import com.turing.code.user.pojo.vo.MerchantProInvite;
import com.turing.code.user.service.UserMerchantInviteService;
import com.turing.code.user.service.UserOrderPayService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserMerchantInviteServiceImpl implements UserMerchantInviteService {

    @Resource
    private TenantInviteMapper tenantInviteMapper;
    @Resource
    private TenantInviteConfigMapper tenantInviteConfigMapper;
    @Resource
    private UserRoyalFlowProMapper userRoyalFlowProMapper;
    @Resource
    private TenantProMapper tenantProMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private HttpServletRequest request;
    @Resource
    private UserOrderPayService userOrderPayService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addInviteFlow(UserOrder userOrder) {
        //查出商品
        List<UserOrderPay> list = userOrderPayService.list(new QueryWrapper<UserOrderPay>().eq(UserOrderPay.ORDER_ID,userOrder.getOrderId()));
        TenantPro merchantPro = tenantProMapper.selectById(list.get(0).getProId());
        if (merchantPro.getIsInvite() != 1) {
            return;//不是分销商品
        }
        //下单用户
        UserInfo orderUser = userInfoMapper.selectById((Long) request.getAttribute("id"));
        if (!CommUtil.checkNull(orderUser.getInviterId())) {
            return;//没有上级
        }
        //上级用户
        UserInfo agentUser = userInfoMapper.selectById(Long.valueOf(orderUser.getInviterId()));

        Map<String, Object> map = new HashMap<String, Object>(8){{
            put("tenantId", merchantPro.getTenantId());
            put("userId", agentUser.getId());
        }};
        //  上级用户分销记录
        TenantInvite agentInvite = tenantInviteMapper.selectOneByMap(map);
        if (agentInvite == null) {
            agentInvite = new TenantInvite().setTenantId(merchantPro.getTenantId()).setShopId(merchantPro.getShopId())
                    .setUserId(agentUser.getId()).setProAmount(BigDecimal.ZERO).setAmount(BigDecimal.ZERO).setGrade(0).setStatus(1);
            if (tenantInviteMapper.insert(agentInvite) != 1) {
                throw new ServiceException();
            }
        } else if (agentInvite.getStatus() != 1) {
            //被取消分销
            return;
        }
        //店铺分销参数
        TenantInviteConfig inviteConfig = tenantInviteConfigMapper.selectByMerchantId(merchantPro.getTenantId().intValue());
        if (inviteConfig == null) {
            return;
        }
        //根据分销员的等级，获取直推佣金比例
        BigDecimal preDirect;
        switch (agentInvite.getGrade()) {
            //等级 0普通  1黄金会员  2合伙人  3至尊合伙人
            case 0 :
                preDirect = inviteConfig.getUserDirect();
                break;
            case 1 :
                preDirect = inviteConfig.getGoldDirect();
                break;
            case 2 :
                preDirect = inviteConfig.getPartnerDirect();
                break;
            case 3 :
                preDirect = inviteConfig.getSuperPartnerDirect();
                break;
            default:
                throw new ServiceException();
        }
        preDirect = preDirect.divide(new BigDecimal(100));

        //直推奖励,保留两位小数向下取整
        BigDecimal rewardDirect = userOrder.getTotalAmount().multiply(preDirect).setScale(2, BigDecimal.ROUND_DOWN);
        //直推流水
        UserRoyalFlowPro userRoyalFlowPro = new UserRoyalFlowPro();

        userRoyalFlowPro.setAgentId(agentInvite.getUserId());
        userRoyalFlowPro.setUserId(orderUser.getId());
        userRoyalFlowPro.setProId(merchantPro.getId());
        userRoyalFlowPro.setTenantId(merchantPro.getTenantId()); // 注意：原代码中的'tenantId'字段访问可能缺少get方法的大小写修正
        userRoyalFlowPro.setShopId(merchantPro.getShopId());
        userRoyalFlowPro.setSecAmount(rewardDirect);
        userRoyalFlowPro.setOrderAmount(userOrder.getTotalAmount());
        userRoyalFlowPro.setType(1);
        userRoyalFlowPro.setCityCode(Integer.valueOf(merchantPro.getCity()));
        userRoyalFlowPro.setOrderId(userOrder.getOrderId());
        //间推业务 ↓↓↓
        if (CommUtil.checkNull(agentUser.getInviterId())) {
            //上上级用户
            UserInfo secAgentUser = userInfoMapper.selectById(Long.valueOf(agentUser.getInviterId()));
            map.put("userId", secAgentUser.getId());
            //上上级用户分销记录
            TenantInvite secInvite = tenantInviteMapper.selectOneByMap(map);
            if (secInvite == null) {
                 secInvite = new TenantInvite();

                secInvite.setTenantId(merchantPro.getTenantId()); // 注意大小写，原为gettenantId()，假设应为getTenantId()
                secInvite.setShopId(merchantPro.getShopId());
                secInvite.setUserId(secAgentUser.getId());
                secInvite.setProAmount(BigDecimal.ZERO);
                secInvite.setAmount(BigDecimal.ZERO);
                secInvite.setGrade(0);
                secInvite.setStatus(1);

                if (tenantInviteMapper.insert(secInvite) != 1) {
                    throw new ServiceException();
                }
            }
            if (secInvite.getStatus() == 1) {
                //根据分销员的等级，获取间推佣金比例
                BigDecimal preIndirect;
                switch (secInvite.getGrade()) {
                    //等级 0普通  1黄金会员  2合伙人  3至尊合伙人
                    case 0 :
                        preIndirect = inviteConfig.getUserIndirect();
                        break;
                    case 1 :
                        preIndirect = inviteConfig.getGoldIndirect();
                        break;
                    case 2 :
                        preIndirect = inviteConfig.getPartnerIndirect();
                        break;
                    case 3 :
                        preIndirect = inviteConfig.getSuperPartnerIndirect();
                        break;
                    default:
                        throw new ServiceException();
                }
                preIndirect = preIndirect.divide(new BigDecimal(100));

                //间推奖励
                BigDecimal rewardIndirect = userOrder.getTotalAmount().multiply(preIndirect).setScale(2, BigDecimal.ROUND_DOWN);
                //间推流水
                UserRoyalFlowPro secUserRoyalFlowPro = new UserRoyalFlowPro();
                BeanUtils.copyProperties(userRoyalFlowPro, secUserRoyalFlowPro);
                secUserRoyalFlowPro.setType(2);
                secUserRoyalFlowPro.setSecAmount(rewardIndirect);
                secUserRoyalFlowPro.setAgentId(secAgentUser.getId());
                secUserRoyalFlowPro.setAmount(rewardDirect.add(rewardIndirect));

                if (userRoyalFlowProMapper.insert(secUserRoyalFlowPro) != 1) {
                    throw new ServiceException();
                }
                userRoyalFlowPro.setAmount(rewardDirect.add(rewardIndirect));
            }
        } else {
            userRoyalFlowPro.setAmount(rewardDirect);
        }
        if (userRoyalFlowProMapper.insert(userRoyalFlowPro) != 1) {
            throw new ServiceException();
        }
    }

    @Override
    public List<Map<String, Object>> selectUserInviteShop(Map<String, Object> map) {
        return tenantInviteMapper.selectUserInviteShop(map);
    }

    @Override
    public List<MerchantProInvite> selectMerchantProInviteList(Map<String, Object> map) {
        return tenantProMapper.selectMerchantProInviteList(map);
    }

    @Override
    public BigDecimal selectSumProAmountByAgentId(Long agentId, Long shopId) {
        return tenantInviteMapper.selectSumProAmountByAgentId(agentId, shopId);
    }

    @Override
    public TenantInvite selectOneByMap(Map<String, Object> map) {
        return tenantInviteMapper.selectOneByMap(map);
    }
}
