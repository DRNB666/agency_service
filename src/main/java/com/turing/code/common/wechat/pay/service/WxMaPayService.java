package com.turing.code.common.wechat.pay.service;

import com.turing.code.admin.pojo.AdFlow;
import com.turing.code.admin.pojo.AdFlowProfit;
import com.turing.code.admin.service.AdFlowProfitService;
import com.turing.code.admin.service.AdFlowService;
import com.turing.code.common.config.KeyConfig;
import com.turing.code.common.ex.ServiceException;
import com.turing.code.system.pojo.WxPayLog;
import com.turing.code.system.service.WxPayLogService;
import com.turing.code.tenant.pojo.*;
import com.turing.code.tenant.service.*;
import com.turing.code.user.pojo.UserInfo;
import com.turing.code.user.service.UserInfoService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Service
public class WxMaPayService {
    @Resource
    private WxPayLogService wxPayLogService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private AdFlowService adFlowService;
    @Resource
    private AdFlowProfitService adFlowProfitService;
    @Resource
    private TenantAuditService tenantAuditService;
    @Resource
    private BCryptPasswordEncoder encoder;
    @Resource
    private TenantInfoService tenantInfoService;
    @Resource
    private TenantShopService tenantShopService;
    @Resource
    private TenantInfoBalanceService tenantInfoBalanceService;
    @Resource
    private TenantVipLevelService tenantVipLevelService;

    /**
     * 商家入驻平台
     */
    @Transactional(rollbackFor = Exception.class)
    public void merchantSettled(WxPayLog wxPayLog) {
        if (!wxPayLogService.save(wxPayLog)) {
            throw new ServiceException();
        }
        TenantAudit merchantFirmAudit = tenantAuditService.getById(wxPayLog.getPromoterAuditId());
        //更改审核状态
        merchantFirmAudit.setPayTime(new Date());
        merchantFirmAudit.setPayStatus(1);
        if (!tenantAuditService.updateById(merchantFirmAudit)) {
            throw new ServiceException();
        }
        //创建商家信息
        TenantInfo merchantInfo = new TenantInfo();
// merchantInfo.setIndustryId(merchantFirmAudit.getIndustryId());
// merchantInfo.setIndustryName(merchantFirmAudit.getIndustryName());
        merchantInfo.setName(merchantFirmAudit.getName());
// merchantInfo.setAddr(merchantFirmAudit.getAddress());
// merchantInfo.setCityCode(Integer.parseInt(merchantFirmAudit.getCityCode()));
// merchantInfo.setCityName(merchantFirmAudit.getCityName());
// merchantInfo.setAreaName(merchantFirmAudit.getAreaName());
// merchantInfo.setAreaCode(Integer.parseInt(merchantFirmAudit.getAreaCode()));
        merchantInfo.setPassword(encoder.encode(merchantFirmAudit.getPassword() + KeyConfig.KEY_PWD));
        merchantInfo.setAccount(merchantFirmAudit.getAccount());
        merchantInfo.setParentId(-1);
        merchantInfo.setRoleId(2); // 主租户角色
// merchantInfo.setAddr(merchantFirmAudit.getAddress());
// merchantInfo.setPhone(merchantFirmAudit.getRemark());

        if (!tenantInfoService.save(merchantInfo)) {
            throw new ServiceException();
        }
        //创建店铺信息
        TenantShop merchantShop = new TenantShop();
        merchantShop.setShopPhoto("/files/default/default_firm_info.png");
        merchantShop.setStatus(2);
        merchantShop.setPhone(merchantFirmAudit.getPhone());
        merchantShop.setAddress(merchantFirmAudit.getAddress());
        merchantShop.setLatitude(merchantFirmAudit.getLatitude());
        merchantShop.setLongitude(merchantFirmAudit.getLongitude());
        merchantShop.setName(merchantFirmAudit.getShopName());
        merchantShop.setTenantId(merchantInfo.getId().longValue());
        merchantShop.setIcon("/files/default/default_map.png");

        if (!tenantShopService.save(merchantShop)) {
            throw new ServiceException();
        }
        //创建商户余额表
        TenantInfoBalance merchantInfoBalance = TenantInfoBalance.builder()
                .balance(new BigDecimal(0))
                .tenantId(merchantInfo.getId().longValue())
                .frozenAmount(new BigDecimal(0))
                .totalAmount(new BigDecimal(0))
                .build();
        if (!tenantInfoBalanceService.save(merchantInfoBalance)) {
            throw new ServiceException();
        }

        //回写用户merchantId
        UserInfo userInfo = userInfoService.getById(merchantFirmAudit.getUsId());
        userInfo.setTenantId(merchantInfo.getId().longValue());
        if (!userInfoService.updateById(userInfo)) {
            throw new ServiceException();
        }

        //创建租户默认会员会员卡分佣
        TenantVipLevel tenantVipLevel = new TenantVipLevel().setTenantId(merchantInfo.getId()).setStatus(0).setIcon("default").setName("基础会员消费").setDay(0)
                .setDiscountRatio(0.00);
        if (!tenantVipLevelService.save(tenantVipLevel)) {
            throw new ServiceException();
        }

        //添加平台流水
        AdFlow adFlow = new AdFlow();
        adFlow.setMoney(wxPayLog.getMoney());
        adFlow.setRemark("商家入驻费用");
        adFlow.setOrderId(merchantFirmAudit.getId());
        if (!adFlowService.save(adFlow)) {
            throw new ServiceException();
        }
        // 添加平台收益流水
        AdFlowProfit adFlowProfit = new AdFlowProfit();
        adFlowProfit.setMoney(wxPayLog.getMoney());
        adFlowProfit.setOrigin("商家入驻费用");
        adFlowProfit.setOriginType(3);
        adFlowProfit.setOrderMoney(wxPayLog.getMoney());
        adFlowProfit.setOrderId(merchantFirmAudit.getId());
        adFlowProfit.setType(1);
        if (!adFlowProfitService.save(adFlowProfit)) {
            throw new ServiceException();
        }
    }

    public void handleOrder(Long orderId, Long userId) {

    }
}
