package com.leepsmart.code.common.liebao;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Leon
 * @apiNote 猎豹API
 */
@Getter
@AllArgsConstructor
public enum LieBaoApi {

    ACCESS_TOKEN("获取AccessToken接口","/v1/access-token"),
    CREATE_OE_LINK("生成OE开户链接","/v1/oe-token/create"),
    FACEBOOK_ACCOUNT_LIST("获取账户猎豹","/v1/facebook-account-list"),
    FACEBOOK_ACCOUNT_SINGLE("获取单个账户信息","/v1/facebook-account-single"),
    FACEBOOK_ACCOUNT_RECHARGE("调整账户花费上限（账户充值、减款）","/v1/facebook-account-recharge"),
    FACEBOOK_ACCOUNT_GRANT("账户绑定BM接口","/v1/facebook-account-grant"),
    BUSINESS_ACCOUNT_BINDINGS("查广告账户绑定的BM","/v1/business-account-bindings");

    private final String remark;
    private final String url;

}
