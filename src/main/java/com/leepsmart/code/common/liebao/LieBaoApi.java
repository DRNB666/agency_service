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

    FACEBOOK_ACCOUNT_LIST("获取账户列表","/v1/facebook-account-list"),

    JOB_LIST("获取开户工单列表","/v1/job-list"),

    JOB_STATUS("获取开户工单状态","/v1/job-status"),

    FACEBOOK_ACCOUNT_SINGLE("获取单个账户信息","/v1/facebook-account-single"),

    FACEBOOK_ACCOUNT_RECHARGE("调整账户花费上限（账户充值、减款）","/v1/facebook-account-recharge"),

    FACEBOOK_ACCOUNT_GRANT("账户绑定BM接口","/v1/facebook-account-grant"),

    BUSINESS_ACCOUNT_BINDINGS("查广告账户绑定的BM","/v1/business-account-bindings"),

    FACEBOOK_ACCOUNT_RESET("账户清零", "/v1/facebook-account-reset"),

    FACEBOOK_ACCOUNT_PIXEL("账户绑定Pixel接口 ", "/v1/facebook-account-pixel"),

    PIXEL_ACCOUNT_BINDINGS("账户和Pixcel绑定关系查询接口 ", "/v1/pixel-account-bindings"),

    FACEBOOK_PIXEL_BIND_BM_JOB("提交Pixel绑定BM工单接口 ", "/v1/facebook-pixel-bind-bm-job"),

    JOB_RESULT("获取Pixel绑定BM工单结果 ", "/v1/job-result"),

    PAY_LIST("获取到款记录 ", "/v1/pay-list"),

    QUOTA("获取剩余可充额度 ", "/v1/quota"),

    INSIGHTS_ACCOUNT("广告账户维度成效查询 ", "/v1/insights/account"),

    INSIGHTS_ACCOUNT_DATE_RANGE("广告账户维度成效查询(日期范围汇总数据) ", "/v1/insights/account-date-range");


    private final String remark;
    private final String url;

}
