package com.leepsmart.code.common.wechat.pay.service;
import com.leepsmart.code.admin.service.AdFlowProfitService;
import com.leepsmart.code.system.service.WxPayLogService;
import com.leepsmart.code.user.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class WxMaPayService {
    @Resource
    private WxPayLogService wxPayLogService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private AdFlowProfitService adFlowProfitService;



}
