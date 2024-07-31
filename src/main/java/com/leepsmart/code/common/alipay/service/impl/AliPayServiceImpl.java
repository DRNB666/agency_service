package com.leepsmart.code.common.alipay.service.impl;

import cn.hutool.log.Log;
import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.leepsmart.code.common.alipay.config.PayConfig;
import com.leepsmart.code.common.alipay.service.AliPayService;
import com.leepsmart.code.common.ex.ServiceException;
import com.leepsmart.code.common.utils.CommUtil;
import com.leepsmart.code.common.utils.LogUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AliPayServiceImpl implements AliPayService {

    @Resource
    private PayConfig payConfig;

    private AlipayConfig createAlipayConfig() {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl(payConfig.getGateway());
        alipayConfig.setAppId(payConfig.getAppId());
        alipayConfig.setPrivateKey(payConfig.getPrivateKey());
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(payConfig.getAliPayPublicKey());
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setSignType("RSA2");
        return alipayConfig;
    }

    @Override
    public String createOrderMobile() {
        AlipayConfig alipayConfig = createAlipayConfig();
        AlipayClient alipayClient = null;
        try {
            alipayClient = new DefaultAlipayClient(alipayConfig);
        } catch (AlipayApiException e) {
            LogUtil.error("支付宝支付:构建alipayClient失败:{}",e);
            throw new ServiceException();
        }
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        //请求参数集合对象,除了公共参数之外,所有参数都可通过此对象传递（不同支付类型需使用不同的请求参数对象）
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        //订单描述
        model.setBody("订单描述");
        //订单标题
        model.setSubject("显示效果见于下图的标题");
        //商户订单号
        String random = CommUtil.createRandom(true, 10);
        model.setOutTradeNo(random);
        //订单的过期时长(取值为5m - 15d,即五分钟到十五天)
        model.setTimeoutExpress("30m");
        //订单总金额
        model.setTotalAmount(String.valueOf(1000.87));
        request.setBizModel(model);
        //支付成功后的跳转地址
        request.setReturnUrl("https://wwww.baidu.com");
        //支付成功后的回调地址（此地址必须为公网地址，用于支付宝收到用户付款之后,通知我们的服务端,我们可以在此接口中更改订单状态为已付款或后续操作）
        request.setNotifyUrl(payConfig.getNotifyUrl());
        String url = null;
        try {
            url = alipayClient.pageExecute(request, "GET").getBody();
            LogUtil.info("生成的二维码:{}",url);
        } catch (AlipayApiException e) {
            LogUtil.error("支付宝支付:构建alipayClient失败:{}",e);
            throw new ServiceException();
        }
        return url;

    }
}
