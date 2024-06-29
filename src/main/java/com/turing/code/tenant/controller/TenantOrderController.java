package com.turing.code.tenant.controller;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.common.annotation.parameterverify.ParameterVerify;
import com.turing.code.common.ex.ServiceException;
import com.turing.code.common.utils.CommUtil;
import com.turing.code.common.utils.page.pojo.PageInfo;
import com.turing.code.common.utils.page.pojo.PageResult;
import com.turing.code.common.utils.returnbody.ResultCodeInfo;
import com.turing.code.common.utils.returnbody.ReturnBody;
import com.turing.code.tenant.pojo.TenantInfo;
import com.turing.code.tenant.pojo.TenantMallSet;
import com.turing.code.tenant.pojo.TenantOrderBills;
import com.turing.code.tenant.pojo.TenantOrderDelivery;
import com.turing.code.tenant.pojo.vo.TenantOrderDevVo;
import com.turing.code.tenant.pojo.vo.TenantOrderVo;
import com.turing.code.tenant.service.TenantMallSetService;
import com.turing.code.tenant.service.TenantOrderBillsService;
import com.turing.code.tenant.service.TenantOrderDeliveryService;
import com.turing.code.user.pojo.UserOrder;
import com.turing.code.user.pojo.UserOrderPay;
import com.turing.code.user.pojo.vo.UserOrderPayDevVo;
import com.turing.code.user.service.UserOrderPayService;
import com.turing.code.user.service.UserOrderService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author turing generator
 * @since 2024-06-14
 */
@Api(tags = "租户-订单")
@ApiResponses({
        @ApiResponse(code = 200, message = "请求成功"),
        @ApiResponse(code = 401, message = "无用户权限"),
        @ApiResponse(code = 403, message = "无资源权限"),
        @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "tenant/order", produces = "text/plain;charset=utf-8")
public class TenantOrderController {

    @Resource
    private UserOrderPayService userOrderPayService;
    @Resource
    private UserOrderService userOrderService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private TenantMallSetService tenantMallSetService;
    @Resource
    private TenantOrderBillsService tenantOrderBillsService;
    @Resource
    private TenantOrderDeliveryService tenantOrderDeliveryService;



    @ApiOperation("订单列表")
    @PostMapping("orderList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = false),
            @ApiImplicitParam(name = "orderId", value = "订单编号", required = false),
            @ApiImplicitParam(name = "status", value = "订单状态", required = false),
            @ApiImplicitParam(name = "type", value = "订单类型：0：普通订单 1：会员礼包订单", required = false),
    })
    public String orderList(PageInfo pageInfo, String phone, Integer orderId, Integer status, Integer type) {
        Integer pId = (Integer) request.getAttribute("pId");
        PageResult<TenantOrderVo> pageResult = new PageResult<>();
        Page<TenantOrderVo> page = new Page<>(pageInfo.getPageNo(), pageInfo.getPageSize());
        //这里是自定义sql
        IPage<TenantOrderVo> iPage = userOrderPayService.orderList(page, new HashMap<String, Object>() {{
            put("phone", phone);
            put("orderId", orderId);
            put("pId", pId);
            put("status", status);
            put("type", type);
        }});
        pageResult.setPageSize(iPage.getSize()).setPageNo(iPage.getCurrent()).setPages(iPage.getPages()).
                setTotal(iPage.getTotal());
        pageResult.setList(iPage.getRecords());
        TenantMallSet tenantMallSet = tenantMallSetService.getOne(new QueryWrapper<TenantMallSet>().eq(TenantMallSet.TENANT_ID, pId));
        //查询实际支付金额
        pageResult.getList().forEach(item -> {
            if (tenantMallSet.getPayType() == 2) {
                //线下支付查账单当前已收款金额
                QueryWrapper<TenantOrderBills> eq = new QueryWrapper<TenantOrderBills>()
                        .eq(TenantOrderBills.ORDER_ID, item.getOrderId()).eq(TenantOrderBills.STATUS, 1);
                List<TenantOrderBills> orderBills = tenantOrderBillsService.list(eq);
                if (CommUtil.checkNull(orderBills)) {
                    item.setActuallyPay(orderBills.stream().map(TenantOrderBills::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
                } else {
                    item.setActuallyPay(BigDecimal.ZERO);
                }
            } else {
                //线上支付查订单金额
                item.setActuallyPay(item.getPayAmount());
            }
            //默认标记发货状态为未完成
            item.setDevStatus(1);
            //判断是否已经发货完毕
            QueryWrapper<TenantOrderDelivery> eq = new QueryWrapper<TenantOrderDelivery>().eq(TenantOrderDelivery.ORDER_ID, item.getOrderId());
            List<TenantOrderDelivery> devList = tenantOrderDeliveryService.list(eq);
            if (CommUtil.checkNull(devList)) {
                //所有已发货的数量
                int sum = devList.stream().mapToInt(TenantOrderDelivery::getCount).sum();
                if (sum >= item.getCount()) {
                    //发货数量已满足，标记为发货完成
                    item.setDevStatus(2);
                }
            }

        });
        return ReturnBody.success(pageResult);
    }

    @ApiOperation("获取子订单列表")
    @PostMapping("queryOrderPayList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单编号", required = true),
    })
    @ParameterVerify(notNull = "orderId")
    public String queryOrderPayList(Long orderId) {
        List<UserOrderPay> list = userOrderPayService.list(new QueryWrapper<UserOrderPay>().eq(UserOrderPay.ORDER_ID, orderId));
        List<UserOrderPayDevVo> userOrderPayDevVos = new ArrayList<>();
        list.forEach(item -> {
            UserOrderPayDevVo userOrderPayDevVo = new UserOrderPayDevVo();
            BeanUtils.copyProperties(item, userOrderPayDevVo);
            //查已发货的数量
            QueryWrapper<TenantOrderDelivery> eq = new QueryWrapper<TenantOrderDelivery>()
                    .eq(TenantOrderDelivery.ORDER_PAY_ID, item.getId());
            List<TenantOrderDelivery> tenantOrderDeliveryS = tenantOrderDeliveryService.list(eq);
            int devCount = tenantOrderDeliveryS.stream().mapToInt(TenantOrderDelivery::getCount).sum();
            userOrderPayDevVo.setHasDevCount(userOrderPayDevVo.getCount() - devCount);
            userOrderPayDevVos.add(userOrderPayDevVo);
        });
        return ReturnBody.success(userOrderPayDevVos);
    }

    @ApiOperation("订单发货")
    @PostMapping("orderDelivery")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "logisticsCompany", value = "物流公司", required = true),
            @ApiImplicitParam(name = "logisticsNumber", value = "物流单号", required = true),
            @ApiImplicitParam(name = "deliveryCategory", value = "发货种类：1：部分发货 2：全部发货", required = true),
            @ApiImplicitParam(name = "devInfo", value = "发货信息", required = true),
    })
    @ParameterVerify(notNull = {"logisticsCompany", "logisticsNumber", "deliveryCategory", "devInfo"})
    public String orderDelivery(String logisticsCompany, String logisticsNumber, Integer deliveryCategory, String devInfo) {
        Integer rId = (Integer) request.getAttribute(TenantInfo.ID);
        JSONArray devInfoArray = JSONArray.parseArray(devInfo);
        if (devInfoArray.size() == 0) {
            return ReturnBody.error("至少选择一个商品发货");
        }
        Long orderId = null;
        List<TenantOrderDelivery> tenantOrderDeliveries = new ArrayList<>();
        List<UserOrderPay> userOrderPays = new ArrayList<>();
        for (Object o : devInfoArray) {
            JSONObject devObj = JSONObject.parseObject(o.toString());
            Long orderPayId = devObj.getLong("id");
            Integer count = devObj.getInteger("devCount");
            if (!CommUtil.checkNull(orderPayId, count)) {
                throw new ServiceException("发货商品参数错误");
            }
            //查出订单详情
            UserOrderPay userOrderPay = userOrderPayService.getById(orderPayId);
            if (!CommUtil.checkNull(userOrderPay)) {
                throw new ServiceException(ResultCodeInfo.PARAM_ERROR.getResult());
            }
            orderId = userOrderPay.getOrderId();
            //添加订单发货信息
            TenantOrderDelivery tenantOrderDelivery = new TenantOrderDelivery().setOperatorId(rId).setCount(count).setOrderPayId(orderPayId).setOrderId(orderId)
                    .setLogisticsCompany(logisticsCompany).setLogisticsNumber(logisticsNumber);
            //只修改订单详情的发货状态，其他地方查发货信息查发货信息表
            userOrderPay.setStatus(2);
            userOrderPays.add(userOrderPay);
            tenantOrderDeliveries.add(tenantOrderDelivery);
        }
        //修改订单信息
        QueryWrapper<UserOrder> eq = new QueryWrapper<UserOrder>().eq(UserOrder.ORDER_ID, orderId);
        UserOrder userOrder = userOrderService.getOne(eq);
        userOrder.setStatus(2).setDeliveryCategory(deliveryCategory);
        if (deliveryCategory == 1) {
            //查出所有部分发货的商品数量，如果发货完成将订单发货类型改成全部发货
            QueryWrapper<TenantOrderDelivery> devEq = new QueryWrapper<TenantOrderDelivery>().eq(TenantOrderDelivery.ORDER_ID, orderId);
            List<TenantOrderDelivery> orderDevList = tenantOrderDeliveryService.list(devEq);
            orderDevList.addAll(tenantOrderDeliveries);
            int sum = orderDevList.stream().mapToInt(TenantOrderDelivery::getCount).sum();
            if (sum >= userOrder.getCount()) {
                userOrder.setDeliveryCategory(2);
            }
        }
        //提交事务
        userOrderService.orderDelivery(tenantOrderDeliveries, userOrderPays, userOrder);
        return ReturnBody.success();
    }

    @ApiOperation("改价")
    @PostMapping("reformPrice")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "freightAmount", value = "运费", required = true),
            @ApiImplicitParam(name = "installAmount", value = "安装费", required = true),
            @ApiImplicitParam(name = "payAmount", value = "实际支付", required = true),
    })
    @ParameterVerify(notNull = {"userOrder.freightAmount", "userOrder.installAmount", "userOrder.payAmount"})
    public String reformPrice(UserOrder userOrder) {
        UserOrder order = userOrderService.getById(userOrder.getId());
        order.setFreightAmount(userOrder.getFreightAmount()).setInstallAmount(userOrder.getInstallAmount())
                .setPayAmount(userOrder.getPayAmount());
        if (!userOrderService.updateById(order)) {
            return ReturnBody.error(ResultCodeInfo.SERVICE_EXCEPTION);
        }
        return ReturnBody.success();
    }

    @ApiOperation("订单详情")
    @PostMapping("orderDetail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true),
    })
        @ParameterVerify(notNull = "id")
    public String orderDetail(Integer id) {
        JSONObject object = new JSONObject(true);
        //主单详情
        UserOrder userOrder = userOrderService.getById(id);
        if (!CommUtil.checkNull(userOrder)) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        object.put("order", userOrder);
        //子单列表
        QueryWrapper<UserOrderPay> eq = new QueryWrapper<UserOrderPay>().eq(UserOrderPay.ORDER_ID, userOrder.getOrderId());
        List<UserOrderPay> userOrders = userOrderPayService.list(eq);
        object.put("userOrders", userOrders);
        //发货信息
        List<TenantOrderDevVo> devList = tenantOrderDeliveryService.devList(userOrder.getOrderId());
        object.put("devList",devList);
        //线下收款进度
        QueryWrapper<TenantOrderBills> billsEq = new QueryWrapper<TenantOrderBills>().eq(TenantOrderBills.ORDER_ID, userOrder.getOrderId());
        List<TenantOrderBills> list = tenantOrderBillsService.list(billsEq);
        object.put("bills",list);
        return ReturnBody.success(object);
    }
}
