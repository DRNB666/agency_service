package com.turing.code.tenant.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.common.utils.page.pojo.SortWay;
import com.turing.code.tenant.pojo.TenantInfo;
import com.turing.code.tenant.pojo.TenantMallSet;
import com.turing.code.tenant.pojo.TenantProSku;
import com.turing.code.tenant.pojo.vo.TenantBillsConfirmVo;
import com.turing.code.tenant.pojo.vo.UserOrderVo;
import com.turing.code.tenant.service.TenantInfoService;
import com.turing.code.tenant.service.TenantMallSetService;
import com.turing.code.user.pojo.UserInfo;
import com.turing.code.user.pojo.UserOrder;
import com.turing.code.user.pojo.UserOrderPay;
import com.turing.code.user.service.UserInfoService;
import com.turing.code.user.service.UserOrderPayService;
import com.turing.code.user.service.UserOrderService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import com.turing.code.tenant.service.TenantOrderBillsService;
import com.turing.code.tenant.pojo.TenantOrderBills;

import javax.servlet.http.HttpServletRequest;

import com.turing.code.common.utils.returnbody.*;
import org.springframework.web.bind.annotation.PostMapping;
import com.turing.code.common.annotation.parameterverify.ParameterVerify;
import com.turing.code.common.utils.page.pojo.PageResult;
import com.turing.code.common.utils.page.pojo.PageInfo;
import com.turing.code.common.utils.page.util.PageUtil;
import com.turing.code.common.utils.CommUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author turing generator
 * @since 2024-06-14
 */
@Api(tags = "租户-订单收款账单")
@ApiResponses({
        @ApiResponse(code = 200, message = "请求成功"),
        @ApiResponse(code = 401, message = "无用户权限"),
        @ApiResponse(code = 403, message = "无资源权限"),
        @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "tenant/tenantOrderBills", produces = "text/plain;charset=utf-8")
public class TenantOrderBillsController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private TenantOrderBillsService tenantOrderBillsService;
    @Resource
    private TenantMallSetService tenantMallSetService;
    @Resource
    private UserOrderService userOrderService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserOrderPayService userOrderPayService;
    @Resource
    private TenantInfoService tenantInfoService;

    @ApiOperation("收款进度列表")
    @PostMapping("collectionProgressList")
    public String collectionProgressList(PageInfo pageInfo, Integer orderId, String phone, Integer receiptStatus) {
        Integer pId = (Integer) request.getAttribute("pId");
        QueryWrapper<TenantMallSet> eq = new QueryWrapper<TenantMallSet>().eq(TenantMallSet.TENANT_ID, pId);
        TenantMallSet tenantMallSet = tenantMallSetService.getOne(eq);
        if (tenantMallSet.getPayType() != 2) {
            return ReturnBody.success();
        }
        //执行分页辅助工具
        PageResult<UserOrder> pageResult = new PageResult<>();
        Page<UserOrder> page = new Page<>(pageInfo.getPageNo(), pageInfo.getPageSize());
        //这里是自定义sql
        IPage<UserOrder> iPage = userOrderService.collectionProgressList(page, pId, orderId, phone, receiptStatus);
        pageResult.setPageSize(iPage.getSize()).setPageNo(iPage.getCurrent()).setPages(iPage.getPages()).
                setTotal(iPage.getTotal());
        pageResult.setList(iPage.getRecords());
        List<UserOrderVo> userOrderVos = new ArrayList<>();
        pageResult.getList().forEach(item -> {
            UserOrderVo userOrderVo = new UserOrderVo();
            BeanUtils.copyProperties(item, userOrderVo);
            //用户名称
            UserInfo userInfo = userInfoService.getById(item.getUsId());
            userOrderVo.setUserName(userInfo.getName());
            //已收款金额
            QueryWrapper<TenantOrderBills> billsQueryWrapper = new QueryWrapper<TenantOrderBills>()
                    .eq(TenantOrderBills.ORDER_ID, item.getOrderId()).eq(TenantOrderBills.STATUS, 1);
            BigDecimal collection = tenantOrderBillsService.list(billsQueryWrapper).stream().map(TenantOrderBills::getAmount).reduce(BigDecimal.ZERO,
                    BigDecimal::add);
            userOrderVo.setCollection(collection);
            //未收款金额
            userOrderVo.setUnCollection(item.getTotalAmount().subtract(collection));
            //商品数量
            QueryWrapper<UserOrderPay> userOrderPayQueryWrapper = new QueryWrapper<UserOrderPay>().eq(UserOrderPay.ORDER_ID, item.getOrderId());
            userOrderVo.setCount(userOrderPayService.count(userOrderPayQueryWrapper));
            userOrderVos.add(userOrderVo);
        });
        PageResult<UserOrderVo> result = new PageResult<>();
        BeanUtils.copyProperties(pageResult, result);
        result.setList(userOrderVos);
        return ReturnBody.success(result);
    }

    @ApiOperation("收款确认列表")
    @PostMapping("confirmList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单编号", required = false),
            @ApiImplicitParam(name = "phone", value = "手机号", required = false),
    })
    public String confirmList(PageInfo pageInfo, String phone, Long orderId) {
        Integer pId = (Integer) request.getAttribute("pId");
        //执行分页辅助工具
        PageResult<TenantBillsConfirmVo> pageResult = new PageResult<>();
        Page<TenantBillsConfirmVo> page = new Page<>(pageInfo.getPageNo(), pageInfo.getPageSize());
        //这里是自定义sql
        IPage<TenantBillsConfirmVo> iPage = tenantOrderBillsService.confirmList(page, new HashMap<String, Object>() {{
            put("pId", pId);
            put("orderId", orderId);
            put("phone", phone);
        }});
        pageResult.setPageSize(iPage.getSize()).setPageNo(iPage.getCurrent()).setPages(iPage.getPages()).
                setTotal(iPage.getTotal());
        pageResult.setList(iPage.getRecords());
        pageResult.getList().forEach(item -> {
            //登记人信息
            TenantInfo register = tenantInfoService.getById(item.getRegistrationId());
            if (CommUtil.checkNull(register)) {
                item.setRegisterAd(register.getName());
            }
            if (CommUtil.checkNull(item.getAuditId())) {
                //审核人信息
                TenantInfo tenantInfo = tenantInfoService.getById(item.getAuditId());
                if (CommUtil.checkNull(tenantInfo)) {
                    item.setAuditAd(tenantInfo.getName());
                }
            }
        });
        return ReturnBody.success(pageResult);
    }

    @ApiOperation("收款")
    @PostMapping("collection")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "amount", value = "收款金额", required = true),
            @ApiImplicitParam(name = "orderId", value = "订单号", required = true),
            @ApiImplicitParam(name = "time", value = "时间", required = true),
            @ApiImplicitParam(name = "remark", value = "备注", required = true),
    })
    public String collection(Long orderId, Long time, String remark, BigDecimal amount) {
        Integer rId = (Integer) request.getAttribute(TenantInfo.ID);
        TenantOrderBills tenantOrderBills = new TenantOrderBills().setAmount(amount).setReceiptTime(new Date(time))
                .setOrderId(orderId).setRemark(remark).setRegistrationId(rId).setStatus(0);
        if (!tenantOrderBillsService.save(tenantOrderBills)) {
            return ReturnBody.error(ResultCodeInfo.SERVICE_EXCEPTION);
        }
        return ReturnBody.success();
    }

    @ApiOperation("审核收款")
    @PostMapping("auditCollection")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "账单id", required = true),
            @ApiImplicitParam(name = "type", value = "1通过 -1驳回", required = true),
    })
    @ParameterVerify(notNull = "id")
    public String auditCollection(Integer id, Integer type) {
        Integer rId = (Integer) request.getAttribute(TenantInfo.ID);
        TenantOrderBills tenantOrderBills = tenantOrderBillsService.getById(id);
        if (!CommUtil.checkNull(tenantOrderBills)) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        tenantOrderBills.setStatus(type);
        tenantOrderBills.setAuditId(rId);
        tenantOrderBills.setAuditTime(new Date());
        tenantOrderBillsService.auditCollection(tenantOrderBills);
        return ReturnBody.success();
    }


    @ApiOperation("删除")
    @PostMapping("delete")
    @ParameterVerify(notNull = "id")
    public String delete(String id) {
        return tenantOrderBillsService.removeById(id) ? ReturnBody.success() : ReturnBody.error();
    }

}
