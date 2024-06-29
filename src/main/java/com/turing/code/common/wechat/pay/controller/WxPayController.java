package com.turing.code.common.wechat.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
import com.turing.code.admin.pojo.AdFlow;
import com.turing.code.admin.pojo.AdFlowProfit;
import com.turing.code.admin.pojo.AdInfo;
import com.turing.code.admin.service.AdFlowProfitService;
import com.turing.code.admin.service.AdFlowService;
import com.turing.code.common.annotation.parameterverify.ParameterVerify;
import com.turing.code.common.ex.ServiceException;
import com.turing.code.common.utils.CacheConstant;
import com.turing.code.common.utils.CommUtil;
import com.turing.code.common.utils.LogUtil;
import com.turing.code.common.utils.UUIDUtil;
import com.turing.code.common.utils.returnbody.ReturnBody;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxScanPayNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.order.WxPayNativeOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.turing.code.common.wechat.pay.service.WxMaPayService;
import com.turing.code.system.pojo.SysParams;
import com.turing.code.system.pojo.WxPayLog;
import com.turing.code.system.service.SysParamsService;
import com.turing.code.system.service.WxPayLogService;
import com.turing.code.tenant.pojo.TenantAudit;
import com.turing.code.tenant.pojo.TenantInfo;
import com.turing.code.tenant.pojo.TenantShop;
import com.turing.code.tenant.service.TenantAuditService;
import com.turing.code.tenant.service.TenantInfoService;
import com.turing.code.tenant.service.TenantShopService;
import com.turing.code.user.pojo.UserInfo;
import com.turing.code.user.pojo.UserOrder;
import com.turing.code.user.pojo.UserOrderPay;
import com.turing.code.user.service.UserInfoService;
import com.turing.code.user.service.UserOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * 微信支付示例代码
 *
 * @author Binary Wang
 */
@Api(tags = "微信支付-通用")
@RestController
@RequestMapping(value = "wx/pay", produces = "text/plain;charset=utf-8")
public class WxPayController {

    @Resource
    private WxPayService wxPayService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private WxPayLogService wxPayLogService;
    @Resource
    private AdFlowProfitService adFlowProfitService;
    @Resource
    private AdFlowService adFlowService;
    @Resource
    private WxMaPayService wxMaPayService;
    @Resource
    private UserOrderService userOrderService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private TenantAuditService tenantAuditService;
    @Resource
    private TenantShopService tenantShopService;
    @Resource
    private SysParamsService sysParamsService;
    @Resource
    private TenantInfoService tenantInfoService;

    @Value("${spring.profiles.active}")
    private String env;
    @Value("${callBack}")
    private String callBack;


    @ApiOperation(value = "查询订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "transactionId", value = "微信订单号"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "outTradeNo", value = "商户系统内部的订单号，当没提供transactionId时需要传这个"),
    })
    @PostMapping("/queryOrder")
    public WxPayOrderQueryResult queryOrder(String transactionId, String outTradeNo) throws WxPayException {
        return this.wxPayService.queryOrder(transactionId, outTradeNo);
    }

    @ApiOperation(value = "统一下单（JSAPI支付）")
    @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "money", value = "支付金额")
    @PostMapping("/createJsApiOrder")
    public String createJsApiOrder(Double money) {
        // TODO 获取OPENID
        String openId = "";
        // 订单号
        String outTradeNo = UUIDUtil.getTimeId().toString();
        // 自定义参数，回调时原样返回
        String attach = "自定义参数，回调时原样返回";
        // 支付金额，单位：分
        BigDecimal totalAmount = BigDecimal.valueOf(money).multiply(BigDecimal.valueOf(100));
        // 回调路径
        String url = request.getRequestURL().toString();
        url = url.substring(0, url.lastIndexOf("/"));
        WxPayUnifiedOrderRequest payRequest;
        try {
            payRequest = WxPayUnifiedOrderRequest.newBuilder().outTradeNo(outTradeNo)
                    .openid(openId).body("在线支付").totalFee(totalAmount.intValue())
                    .spbillCreateIp(InetAddress.getLocalHost().getHostAddress()).tradeType("JSAPI")
                    .productId("10000").notifyUrl(url + "/public/notifyPay")
                    .attach(attach).build();
            WxPayMpOrderResult order = this.wxPayService.createOrder(payRequest);
//            order.setAppId(null);
            return ReturnBody.success(order);
        } catch (UnknownHostException | WxPayException e) {
            e.printStackTrace();
            return ReturnBody.error("统一下单失败");
        }
    }
    @ApiOperation(value = "统一下单（订单支付）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Long.class, name = "id", value = "订单id", required = true),
    })
    @PostMapping("/orderPay")
    @ParameterVerify(notNull = {"id"})
    public String orderPay(Long id) {
        Long usId = (Long) request.getAttribute("id");
        // 获取相关费用
        UserOrder userOrderPay = userOrderService.getById(id);
        if (!CommUtil.checkNull(userOrderPay) || userOrderPay.getStatus() != 0) {
            return ReturnBody.error("订单状态错误");
        }
        // 获取用户信息
        UserInfo userInfo = userInfoService.getById(usId);
        if (!CommUtil.checkNull(userInfo.getOpenId())) {
            return ReturnBody.error("未进行微信授权，请先授权");
        }

        // OPENID
        String openId = userInfo.getOpenId();
        // 订单号
        String outTradeNo = CommUtil.getTimeId().toString();
        // 自定义参数，回调时原样返回
        JSONObject attach = new JSONObject(true);
        attach.put("us_id", usId);
        attach.put("order_id", userOrderPay.getId());
        attach.put("newOrderId", outTradeNo);
        attach.put("payMoney", userOrderPay.getPayAmount());
        attach.put("type", "orderPay");
        // 支付金额，单位：分
        BigDecimal totalAmount = userOrderPay.getPayAmount().multiply(BigDecimal.valueOf(100));
        WxPayUnifiedOrderRequest payRequest;
        try {
            payRequest = WxPayUnifiedOrderRequest.newBuilder().outTradeNo(outTradeNo)
                    .openid(openId).body("在线支付").totalFee(totalAmount.intValue())
                    .spbillCreateIp(InetAddress.getLocalHost().getHostAddress()).tradeType("JSAPI")
                    .productId("10000").notifyUrl(callBack + "/public/notifyPay")
                    .attach(attach.toJSONString()).build();

            if ("dev".equals(env)) {
                payRequest.setTotalFee(1);
            }

            WxPayMpOrderResult order = this.wxPayService.createOrder(payRequest);
            order.setAppId(null);
            LogUtil.info("获取预支付订单号成功");
            return ReturnBody.success(order);
        } catch (UnknownHostException | WxPayException e) {
            e.printStackTrace();
            return ReturnBody.error("统一下单失败");
        }
    }

    @ApiOperation("商家入驻")
    @PostMapping("tenantJoin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lng", value = "经度", required = true),
            @ApiImplicitParam(name = "lat", value = "维度", required = true),
            @ApiImplicitParam(name = "name", value = "真实姓名", required = true),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true),
            @ApiImplicitParam(name = "account", value = "手机号", required = true),
            @ApiImplicitParam(name = "password", value = "手机号", required = true),
            @ApiImplicitParam(name = "address", value = "手机号", required = true),
    })
    @ParameterVerify(notNull = {"phone", "account", "password", "address", "lat", "lng"})
    public String tenantJoin(String name, String phone, String account, String password, String address, String lat, String lng) {
        TenantAudit merchantFirmAudit = new TenantAudit().setAccount(account).setPassword(password).setAddress(address).setLatitude(lat)
                .setLongitude(lng).setName(name).setPhone(phone);
        // 获取用户信息
        UserInfo userInfo = (UserInfo) request.getAttribute("info");
        if (userInfo.getTenantId() != null && userInfo.getTenantId() != -1) {
            return ReturnBody.error("您已经是商家了");
        }
        merchantFirmAudit.setUsId(userInfo.getId());
//
//        // 判断验证码是否有效
//        if (!SignDecodeUtil.codeMatches(phone + phoneCode, phoneSign, 600000)) {
//            return ReturnBody.error(ResultCodeInfo.SMS_CODE_ERROR);
//        }
        // 检测该手机号是否已被绑定
        QueryWrapper<TenantShop> eq = new QueryWrapper<TenantShop>().eq(TenantShop.PHONE, phone);
        if (CommUtil.checkNull(tenantShopService.getOneForUpdate(eq))) {
            return ReturnBody.error("该手机号已被绑定");
        }

        //账号查重
        QueryWrapper<TenantInfo> accountEq = new QueryWrapper<TenantInfo>().eq(TenantInfo.ACCOUNT, account);
        if (CommUtil.checkNull(tenantInfoService.getOne(accountEq))) {
            return ReturnBody.error("账号已存在");
        }
        //市区行政代码处理
//        String[] split = merchantFirmAudit.getCityName().split("-");
//        try {
//            SysLocation areaSys = sysLocationMapper.selectSelective(SysLocation.builder().val(areaCode).build()).get(0);
//            SysLocation citySys = sysLocationMapper.selectSelective(SysLocation.builder().val(areaSys.getParentVal()).build()).get(0);
//            merchantFirmAudit.setCityName(citySys.getName());
//            merchantFirmAudit.setCityCode(citySys.getVal());
//            merchantFirmAudit.setAreaName(areaSys.getName());
//            merchantFirmAudit.setAreaCode(areaSys.getVal());
//            merchantFirmAudit.setRemark(phone);
//        } catch (IndexOutOfBoundsException e) {
//            LogUtil.error("地址错误!" + merchantFirmAudit.getCityName() + areaCode);
//            return ReturnBody.error("地址错误!");
//        }


        if (!tenantAuditService.save(merchantFirmAudit)) {
            throw new ServiceException();
        }
        //无邀请码，支付金额后创建商家信息
        SysParams sysParams = sysParamsService.getById(CacheConstant.MERCHANT_CONST);

        if (!CommUtil.checkNull(userInfo.getOpenId())) {
            return ReturnBody.error("未进行微信授权，请先授权");
        }
        // OPENID
        String openId = userInfo.getOpenId();
        // 订单号
        String outTradeNo = CommUtil.getTimeId().toString();
        // 自定义参数，回调时原样返回
        JSONObject attach = new JSONObject(true);
        attach.put("us_id", userInfo.getId());
        attach.put("pay_id", merchantFirmAudit.getId());
        attach.put("newOrderId", outTradeNo);
        attach.put("type", "merchantAudit");
        attach.put("payMoney", new BigDecimal(sysParams.getValue()));
        // 支付金额，单位：分
        BigDecimal totalAmount = new BigDecimal(sysParams.getValue()).multiply(BigDecimal.valueOf(100));
        WxPayUnifiedOrderRequest payRequest;
        try {
            if ("dev".equals(env)) {
                totalAmount = new BigDecimal(1);
            }
            payRequest = WxPayUnifiedOrderRequest.newBuilder().outTradeNo(outTradeNo)
                    .openid(openId).body("在线支付").totalFee(totalAmount.intValue())
                    .spbillCreateIp(InetAddress.getLocalHost().getHostAddress()).tradeType("JSAPI")
                    .productId("10000").notifyUrl(callBack + "/public/notifyPay")
                    .attach(attach.toJSONString()).build();
            WxPayMpOrderResult order = wxPayService.createOrder(payRequest);
            order.setAppId(null);
            LogUtil.info("回调成功");
            return ReturnBody.success(order);
        } catch (UnknownHostException | WxPayException e) {
            e.printStackTrace();
            return ReturnBody.error("统一下单失败");
        }
    }



    @ApiOperation(value = "统一下单（app支付）")
    @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "money", value = "支付金额")
    @PostMapping("/createAppApiOrder")
    public String createAppApiOrder(Double money) {
        // TODO 获取OPENID
        String openId = "";
        // 订单号
        String outTradeNo = UUIDUtil.getTimeId().toString();
        // 自定义参数，回调时原样返回
        String attach = "自定义参数，回调时原样返回";
        // 支付金额，单位：分
        BigDecimal totalAmount = BigDecimal.valueOf(money).multiply(BigDecimal.valueOf(100));
        // 回调路径
        String url = request.getRequestURL().toString();
        url = url.substring(0, url.lastIndexOf("/"));
        WxPayUnifiedOrderRequest payRequest;
        try {
            payRequest = WxPayUnifiedOrderRequest.newBuilder().outTradeNo(outTradeNo)
                    .openid(openId).body("在线支付").totalFee(totalAmount.intValue())
                    .spbillCreateIp(InetAddress.getLocalHost().getHostAddress()).tradeType("APP")
                    .productId("10000").notifyUrl(url + "/public/notifyPay")
                    .attach(attach).build();
            WxPayMpOrderResult order = this.wxPayService.createOrder(payRequest);
//            order.setAppId(null);
            return ReturnBody.success(order);
        } catch (UnknownHostException | WxPayException e) {
            e.printStackTrace();
            return ReturnBody.error("统一下单失败");
        }
    }

    @ApiIgnore
    @ApiOperation(value = "支付回调通知处理")
    @PostMapping("/public/notifyPay")
    public String parseOrderNotifyResult(@RequestBody String xmlData) throws WxPayException {
        final WxPayOrderNotifyResult notifyResult = this.wxPayService.parseOrderNotifyResult(xmlData);
        if (!CommUtil.checkNull(notifyResult) || notifyResult == null) {
            return ReturnBody.error("支付失败");
        }
        System.out.println("================");
        String returnCode = notifyResult.getReturnCode();
        if ("SUCCESS".equals(returnCode)) {
            // 解析Attach参数
            JSONObject attach = JSONObject.parseObject(notifyResult.getAttach());
            String type = attach.getString("type");
            Long newOrderId = attach.getLong("newOrderId");
            LogUtil.info("支付回调成功000" + type);

            switch (type) {
                case "merchantAudit": {
                    //商家入驻逻辑
                    QueryWrapper<WxPayLog> eq = new QueryWrapper<WxPayLog>()
                            .eq(WxPayLog.PROMOTER_AUDIT_ID, attach.getLong("pay_id")).eq(WxPayLog.TYPE,5);
                    if (wxPayLogService.count(eq) == 0) {
                        Long pay_id = attach.getLong("pay_id");
                        WxPayLog wxPayLog = WxPayLog.builder()
                                .money(attach.getBigDecimal("payMoney"))
                                .openId(notifyResult.getOpenid())
                                .userId(attach.getLong("us_id"))
                                .type(5).promoterAuditId(pay_id)
                                .remark("商户入驻保证金")
                                .outTradeNo(newOrderId)
                                .build();
                        LogUtil.info("审核id:" + pay_id);
                        wxMaPayService.merchantSettled(wxPayLog);
                        //添加微信千分之六手续费
                        adFlowService.addWxServiceMoney(wxPayLog.getMoney(), wxPayLog.getPromoterAuditId(), 2, 3);
                    }
                    return WxPayNotifyResponse.success("支付成功");
                }
                case "orderPay": {
                    //普通订单支付逻辑
                    Long orderId = attach.getLong("order_id");
                    QueryWrapper<WxPayLog> eq = new QueryWrapper<WxPayLog>()
                            .eq(WxPayLog.ORDER_ID, orderId).eq(WxPayLog.TYPE,1);
                    if (wxPayLogService.count(eq) == 0) {
                        WxPayLog wxPayLog = WxPayLog.builder()
                                .userId(attach.getLong("us_id"))
                                .orderId(orderId)
                                .type(1).openId(notifyResult.getOpenid())
                                .money(attach.getBigDecimal("payMoney"))
                                .remark("普通订单支付")
                                .outTradeNo(newOrderId)
                                .build();
                        if (!wxPayLogService.save(wxPayLog)) {
                            throw new ServiceException();
                        }
                        wxMaPayService.handleOrder(wxPayLog.getOrderId(), wxPayLog.getUserId());
                        //添加微信千分之六手续费
                        adFlowService.addWxServiceMoney(wxPayLog.getMoney(), wxPayLog.getOrderId(), 2, 1);
                    }
                    return WxPayNotifyResponse.success("订单支付成功");
                }
            }
        }
        return WxPayNotifyResponse.success("成功");
    }

    @ApiOperation(value = "会员充值")
    @PostMapping("us/memberRecharge/qrCode")
    public String memberRecharge(Double money) {
        Long usId = (Long) request.getAttribute(AdInfo.ID);
        JSONObject attach = new JSONObject();
        attach.put("usId", usId);
        attach.put("type", 1);
        String body = "会员充值";
        return createQrCodeOrder(money, attach.toJSONString(), body);
    }


    /**
     * 扫码支付
     *
     * @param money  金额
     * @param attach 自定义参数
     * @param body
     * @return
     */
    public String createQrCodeOrder(Double money, String attach, String body) {
        if (money < 0.01) {
            return ReturnBody.error("输入的金额不正确");
        }
        // 订单号
        String outTradeNo = UUIDUtil.getCode();
        // 支付金额，单位：分
        BigDecimal totalAmount = BigDecimal.valueOf(money).multiply(BigDecimal.valueOf(100));
        // 回调路径
        String url = request.getRequestURL().toString();
        url = url.substring(0, url.lastIndexOf("/"));
        WxPayUnifiedOrderRequest payRequest;
        try {
            payRequest = WxPayUnifiedOrderRequest.newBuilder()
                    .outTradeNo(outTradeNo)
                    .body(body)
                    .totalFee(totalAmount.intValue())
                    .spbillCreateIp(InetAddress.getLocalHost().getHostAddress())
                    .tradeType("NATIVE")
                    .productId("10000")
                    .notifyUrl(url + "/public/notifyQrPay")
                    .attach(attach)
                    .build();
            WxPayNativeOrderResult order = this.wxPayService.createOrder(payRequest);
            return ReturnBody.success(order.getCodeUrl());
        } catch (UnknownHostException | WxPayException e) {
            e.printStackTrace();
            return ReturnBody.error("统一下单失败");
        }
    }


    @ApiIgnore
    @ApiOperation(value = "扫码支付回调通知处理")
    @PostMapping("/public/notifyQrPay")
    public String parseScanPayNotifyResult(String xmlData) throws WxPayException {
        final WxScanPayNotifyResult result = this.wxPayService.parseScanPayNotifyResult(xmlData);
        // TODO 业务逻辑处理，注意幂等性验证

        return WxPayNotifyResponse.success("成功");
    }

}

