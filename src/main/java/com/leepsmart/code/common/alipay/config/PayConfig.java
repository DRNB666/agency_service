package com.leepsmart.code.common.alipay.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 支付宝配置类
 * @author Leon
 */
@Component
@Data
@ToString
public class PayConfig {
    @Value("${ali.easyPay.appId}")
    private String appId;
    @Value("${ali.easyPay.privateKey}")
    private String privateKey;
    @Value("${ali.easyPay.aliPayPublicKey}")
    private String aliPayPublicKey;
    @Value("${ali.easyPay.gateway}")
    private String gateway;
    @Value("${ali.easyPay.notifyUrl}")
    private String notifyUrl;
}
