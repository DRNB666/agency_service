package com.turing.code.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.tenant.pojo.TenantOrderDelivery;
import com.turing.code.tenant.pojo.TenantProSku;
import com.turing.code.user.pojo.UserOrder;
import com.turing.code.common.mybatisplus.methods.CommonService;
import com.turing.code.user.pojo.UserOrderPay;

import java.util.List;

/**
 * @author turing generator
 * @since 2024-06-13
 */
public interface UserOrderService extends CommonService<UserOrder> {

    /**
     * 购物车提交订单
     */
    void shopCarSettlement(UserOrder order, List<UserOrderPay> userOrderPays, List<TenantProSku> tenantProSkus);

    /**
     * 提交订单
     */
    void submitOrder(UserOrder order, UserOrderPay userOrderPay);

    /**
     * 收款进度列表
     */
    IPage<UserOrder> collectionProgressList(Page<UserOrder> page, Integer tenantId, Integer orderId, String phone, Integer receiptStatus);

    /**
     * 处理佣金
     */
    void handleOrder(UserOrder userOrder);

    /**
     * 订单发货
     */
    void orderDelivery(List<TenantOrderDelivery> tenantOrderDeliveries, List<UserOrderPay> userOrderPays, UserOrder userOrder);
}
