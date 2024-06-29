package com.turing.code.user.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.zxing.WriterException;
import com.turing.code.admin.pojo.AdFlow;
import com.turing.code.admin.pojo.AdFlowProfit;
import com.turing.code.admin.service.AdFlowProfitService;
import com.turing.code.admin.service.AdFlowService;
import com.turing.code.common.ex.ServiceException;
import com.turing.code.common.utils.CacheConstant;
import com.turing.code.common.utils.CommUtil;
import com.turing.code.common.utils.returnbody.ResultCodeInfo;
import com.turing.code.system.pojo.SysParams;
import com.turing.code.tenant.mapper.TenantInfoBalanceMapper;
import com.turing.code.tenant.pojo.*;
import com.turing.code.tenant.service.*;
import com.turing.code.user.mapper.UserRoyalFlowProMapper;
import com.turing.code.user.pojo.UserOrder;
import com.turing.code.user.mapper.UserOrderMapper;
import com.turing.code.user.pojo.UserOrderPay;
import com.turing.code.user.pojo.UserRoyalFlowPro;
import com.turing.code.user.pojo.UserShopCar;
import com.turing.code.user.service.UserMerchantInviteService;
import com.turing.code.user.service.UserOrderPayService;
import com.turing.code.user.service.UserOrderService;
import com.turing.code.common.mybatisplus.methods.CommonServiceImpl;
import com.turing.code.user.service.UserShopCarService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author turing generator
 * @since 2024-06-13
 */
@Service
public class UserOrderServiceImpl extends CommonServiceImpl<UserOrderMapper, UserOrder> implements UserOrderService {

    @Resource
    private UserOrderPayService userOrderPayService;
    @Resource
    private TenantProSkuService tenantProSkuService;
    @Resource
    private UserShopCarService userShopCarService;
    @Resource
    private UserOrderMapper userOrderMapper;
    @Resource
    private UserMerchantInviteService userMerchantInviteService;
    @Resource
    private TenantInfoFlowingService tenantInfoFlowingService;
    @Resource
    private AdFlowService adFlowService;
    @Resource
    private UserRoyalFlowProMapper userRoyalFlowProMapper;
    @Resource
    private TenantInfoBalanceMapper tenantInfoBalanceMapper;
    @Resource
    private TenantInfoService tenantInfoService;
    @Resource
    private TenantShopService tenantShopService;
    @Resource
    private TenantBalanceFlowingService tenantBalanceFlowingService;
    @Resource
    private AdFlowProfitService adFlowProfitService;
    @Resource
    private TenantOrderDeliveryService tenantOrderDeliveryService;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void shopCarSettlement(UserOrder order, List<UserOrderPay> userOrderPays, List<TenantProSku> tenantProSkus) {
        //添加订单
        if (!save(order)) {
            throw new ServiceException();
        }
        //添加订单详情
        if (!userOrderPayService.saveBatch(userOrderPays)) {
            throw new ServiceException();
        }
        //扣减规格库存
        if (!tenantProSkuService.updateBatchById(tenantProSkus)) {
            throw new ServiceException();
        }
        //清空购物车
        QueryWrapper<UserShopCar> eq = new QueryWrapper<UserShopCar>().eq(UserShopCar.USER_ID, order.getUsId());
        if (!userShopCarService.remove(eq)) {
            throw new ServiceException();
        }
        //处理分销员，分销商品
        userMerchantInviteService.addInviteFlow(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitOrder(UserOrder order, UserOrderPay userOrderPay) {
        if (!save(order)) {
            throw new ServiceException();
        }
        if (!userOrderPayService.save(userOrderPay)) {
            throw new ServiceException();
        }
        //处理分销员，分销商品
        userMerchantInviteService.addInviteFlow(order);
    }

    @Override
    public IPage<UserOrder> collectionProgressList(Page<UserOrder> page, Integer tenantId, Integer orderId, String phone, Integer receiptStatus) {
        return userOrderMapper.collectionProgressList(page, tenantId, orderId, phone, receiptStatus);
    }

    @Override
    public void handleOrder(UserOrder userOrderPay) {
        Long orderId = userOrderPay.getOrderId();
        Long usId = userOrderPay.getUsId();

        // 添加商家流水
        TenantInfoFlowing mechantInfoFlowing = new TenantInfoFlowing();

        mechantInfoFlowing.setTenantId(userOrderPay.getTenantId().longValue());
        mechantInfoFlowing.setStatus(1);
        mechantInfoFlowing.setAmount(userOrderPay.getTotalAmount());
        mechantInfoFlowing.setType(3);
        mechantInfoFlowing.setDescr("用户购买商品"); // 注意：假设Descr属性名正确，通常Java中会采用小驼峰命名法，此处按原样保留
        mechantInfoFlowing.setOrderId(userOrderPay.getOrderId());
        if (!tenantInfoFlowingService.save(mechantInfoFlowing)) {
            throw new ServiceException();
        }
        // 添加商家服务流水，总的分佣金额为百分之8
        TenantInfoFlowing mechantAdFlowing = new TenantInfoFlowing();
        mechantAdFlowing.setTenantId(userOrderPay.getTenantId().longValue());
        mechantAdFlowing.setOrderId(userOrderPay.getOrderId());
        mechantAdFlowing.setAmount(userOrderPay.getTotalAmount().multiply(new BigDecimal("0.08"))); // 计算分佣金额
        mechantAdFlowing.setType(3);
        mechantAdFlowing.setDescr("平台服务费");
        mechantAdFlowing.setStatus(2);
        if (!tenantInfoFlowingService.save(mechantAdFlowing)) {
            throw new ServiceException();
        }

        //添加平台流水
        AdFlow adFlow = new AdFlow();
        adFlow.setMoney(userOrderPay.getTotalAmount());
        adFlow.setRemark("用户购买商品");
        adFlow.setType(3);
        adFlow.setOrderType(1);
        adFlow.setOrderId(userOrderPay.getOrderId());
        if (!adFlowService.save(adFlow)) {
            throw new ServiceException();
        }

        //转入商家冻结余额
        Map<String, Object> map = new HashMap<>(8);
        map.put("tenantId", userOrderPay.getTenantId());
        List<TenantInfoBalance> merchantInfoBalances = tenantInfoBalanceMapper.selectByMap(map);
        //商家余额记录
        TenantInfoBalance merchantInfoBalance = merchantInfoBalances.get(0);
        TenantInfo merchantInfo = tenantInfoService.getById(userOrderPay.getTenantId());
        //修改前余额
        BigDecimal oldAmount = merchantInfoBalance.getBalance();

        //添加各平台分佣
        this.grantReward(userOrderPay, merchantInfoBalance, merchantInfo);
        //普通订单把佣金状态变为冻结中     type 1普通订单
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("orderId", userOrderPay.getId());
        queryMap.put("status", 0);
        List<UserRoyalFlowPro> royalFlowProList = userRoyalFlowProMapper.selectByMap(queryMap);
        if (!royalFlowProList.isEmpty()) {
            for (UserRoyalFlowPro userRoyalFlowPro : royalFlowProList) {
                userRoyalFlowPro.setStatus(1);
                userRoyalFlowProMapper.updateById(userRoyalFlowPro);

                merchantInfoBalance.setBalance(merchantInfoBalance.getBalance().subtract(userRoyalFlowPro.getSecAmount()));
                //添加商家余额流水
                TenantInfoFlowing mechantInviteFlowing = new TenantInfoFlowing();

                mechantInviteFlowing.setTenantId(userOrderPay.getTenantId().longValue());
                mechantInviteFlowing.setType(3);
                mechantInviteFlowing.setAmount(userRoyalFlowPro.getSecAmount());
                String descrText = userRoyalFlowPro.getType() == 1 ? "直推" : "间推";
                mechantInviteFlowing.setDescr("发放" + descrText + "佣金");
                mechantInviteFlowing.setOrderId(userOrderPay.getOrderId());
                mechantInviteFlowing.setStatus(3);
                if (!tenantInfoFlowingService.save(mechantInviteFlowing)) {
                    throw new ServiceException();
                }
            }
        }


        //添加商家余额收入流水
        TenantInfo tenantInfo = tenantInfoService.getById(userOrderPay.getTenantId());
        QueryWrapper<TenantShop> eq = new QueryWrapper<TenantShop>().eq(TenantShop.TENANT_ID, tenantInfo.getId());
        TenantShop shop = tenantShopService.getOne(eq);
        TenantBalanceFlowing balanceFlowing = new TenantBalanceFlowing();

        balanceFlowing.setTenantId(userOrderPay.getTenantId().longValue());
        balanceFlowing.setAmount(merchantInfoBalance.getBalance().subtract(oldAmount));
        balanceFlowing.setType(3);
        balanceFlowing.setOrderId(userOrderPay.getOrderId());
        balanceFlowing.setShopName(shop.getName());
        if (!tenantBalanceFlowingService.save(balanceFlowing)) {
            throw new ServiceException();
        }
//        //发送微信模板消息提醒
//        this.sendOrderInfo(userOrderPay);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void orderDelivery(List<TenantOrderDelivery> tenantOrderDeliveries, List<UserOrderPay> userOrderPays, UserOrder userOrder) {
        if (CommUtil.checkNull(tenantOrderDeliveries)){
            if (!tenantOrderDeliveryService.saveBatch(tenantOrderDeliveries)) {
                throw new ServiceException();
            }
        }
        if (CommUtil.checkNull(userOrderPays)){
            if (!userOrderPayService.updateBatchById(userOrderPays)) {
                throw new ServiceException();
            }
        }
        if (userOrderMapper.updateById(userOrder)!=1) {
            throw new ServiceException();
        }
    }


    /**
     * 分配平台抽成
     */
    @Transactional(rollbackFor = Exception.class)
    public void grantReward(UserOrder userOrderPay, TenantInfoBalance merchantInfoBalance, TenantInfo merchantInfo) {
        //用户支付的钱,要减去集体抽佣和平台抽佣,市级代理后，剩余的才转入商家，总的分佣金额为百分之8
        BigDecimal payAmount = userOrderPay.getTotalAmount().multiply(new BigDecimal("0.08")).setScale(2, BigDecimal.ROUND_DOWN);
        //添加平台收益流水
        AdFlowProfit adFlowProfit = new AdFlowProfit();

        adFlowProfit.setMoney(payAmount);
        adFlowProfit.setOrigin("商品交易分配");
        adFlowProfit.setOrderMoney(userOrderPay.getTotalAmount());
        adFlowProfit.setOrderId(userOrderPay.getOrderId());
        adFlowProfit.setType(3);
        adFlowProfit.setOriginType(1);
        if (adFlowProfitService.save(adFlowProfit)) {
            throw new ServiceException();
        }
    }
}
