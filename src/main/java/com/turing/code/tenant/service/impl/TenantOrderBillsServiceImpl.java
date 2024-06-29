package com.turing.code.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.common.ex.ServiceException;
import com.turing.code.tenant.pojo.TenantOrderBills;
import com.turing.code.tenant.mapper.TenantOrderBillsMapper;
import com.turing.code.tenant.pojo.vo.TenantBillsConfirmVo;
import com.turing.code.tenant.service.TenantOrderBillsService;
import com.turing.code.common.mybatisplus.methods.CommonServiceImpl;
import com.turing.code.user.pojo.UserOrder;
import com.turing.code.user.pojo.UserOrderPay;
import com.turing.code.user.pojo.UserRoyalFlow;
import com.turing.code.user.service.UserMerchantInviteService;
import com.turing.code.user.service.UserOrderPayService;
import com.turing.code.user.service.UserOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author turing generator
 * @since 2024-06-14
 */
@Service
public class TenantOrderBillsServiceImpl extends CommonServiceImpl<TenantOrderBillsMapper, TenantOrderBills> implements TenantOrderBillsService {

    @Resource
    private TenantOrderBillsMapper tenantOrderBillsMapper;
    @Resource
    private UserOrderService userOrderService;
    @Resource
    private UserOrderPayService userOrderPayService;
    @Resource
    private UserMerchantInviteService userMerchantInviteService;

    @Override
    public IPage<TenantBillsConfirmVo> confirmList(Page<TenantBillsConfirmVo> page, HashMap<String, Object> stringObjectHashMap) {
        return tenantOrderBillsMapper.confirmList(page, stringObjectHashMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditCollection(TenantOrderBills tenantOrderBills) {
        if (tenantOrderBillsMapper.updateById(tenantOrderBills) != 1) {
            throw new ServiceException();
        }
        //如果账单全部收款完成，那么修改订单状态为已收款
        QueryWrapper<TenantOrderBills> eq = new QueryWrapper<TenantOrderBills>()
                .eq(TenantOrderBills.ORDER_ID, tenantOrderBills.getOrderId()).eq(TenantOrderBills.STATUS,1);
        List<TenantOrderBills> list = list(eq);
        list.add(tenantOrderBills);
        BigDecimal sum = list.stream().map(TenantOrderBills::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        //查询订单总金额
        UserOrder userOrder = userOrderService.getOne(new QueryWrapper<UserOrder>().eq(UserOrder.ORDER_ID, tenantOrderBills.getOrderId()));
        if (sum.compareTo(userOrder.getTotalAmount())>=0&&userOrder.getStatus()==0){
            userOrder.setStatus(1);
            userOrder.setPayTime(new Date());
            //计算佣金
            userOrderService.handleOrder(userOrder);
            UpdateWrapper<UserOrderPay> updateEq = new UpdateWrapper<UserOrderPay>()
                    .eq(UserOrderPay.ORDER_ID, userOrder.getOrderId())
                    .set(UserOrderPay.STATUS,1);
            if (!userOrderPayService.update(updateEq)) {
                throw new ServiceException();
            }
        }

    }
}
