package com.leepsmart.code.common.alipay.service;

import com.alipay.api.AlipayApiException;

public interface AliPayService {

    /**
     * 创建手机网站支付订单并返回url地址
     */
    String createOrderMobile() throws AlipayApiException;
}
