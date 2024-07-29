package com.leepsmart.code.common.utils;

import java.text.SimpleDateFormat;

public interface CacheConstant {

    SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //用户已阅读资讯
    String USER_ARTICLE_READ = "user:article:read";
    //用户已阅读视频集锦
    String USER_VIDEO_READ = "user:video:read";

    //已推荐资讯
    String USER_ARTICLE_RECOMM = "user:article:recomm";
    //已推荐视频集锦
    String USER_VIDEO_RECOMM = "user:video:recomm";

    //防止推荐用户重复商品
    String USER_PRO_SCORE_LOW = "user:pro:score:low";
    String USER_PRO_SCORE_HIGH = "user:pro:score:high";
    //用户当前浏览的城市
    String USER_CITY = "user:city";

    String CITY_VISIT_COUNT = "city:visit:count";


    //商家提现到银行卡最低手续费
    Integer MERCHANT_COST_LIMIT = 1;
    //商家提现到银行卡手续费比例
    Integer MERCHANT_COST_PROPORTION = 2;

    //普通会员集体直推佣金
    Integer FIRM_DIRECT_RECOMMEND = 9;
    //普通会员集体间推佣金
    Integer FIRM_INDIRECT_RECOMMEND = 10;

    //市级代理交易流水抽成比例(除去集体和市级代理剩下的是平台)
    Integer CITY_TRADING_DRAW = 11;
    //集体交易流水抽成比例(除去集体和市级代理剩下的是平台)
    Integer FIRM_TRADING_DRAW = 12;

    //集体推广分配比例（竞价广告，同城推送） (1 = 100%)
    Integer FIRM_SERVICE_COST = 13;
    //市级代理推广分配比例（竞价广告，同城推送） (1 = 100%)
    Integer CITY_SERVICE_COST = 14;

    //集体入驻费用
    Integer FIRM_JOIN_COST = 15;
    //集体入驻协议
    Integer FIRM_JOIN_PROTOCOL = 16;
    //商家入驻协议
    Integer MER_JOIN_PROTOCOL = 21;
    //用户协议
    Integer USER_JOIN_PROTOCOL = 22;




    //平台打款账号
    Integer MONEY_ACCOUNT = 23;
    //城市运营直推佣金
    Integer CITY_DIRECT_RECOMMEND = 24;
    //城市运营间推佣金
    Integer CITY_INDIRECT_RECOMMEND = 25;
    //平台专家推广二维码
    Integer QR_CODE = 26;
    //打款信息开户人
    Integer ACCOUNT_OPENER = 27;
    //平台打款银行
    Integer BANK_ACCOUNT = 28;
    //商户入驻集体保证金
    Integer MERCHANT_CONST = 29;
    //散户入驻商会过期时间
    Integer MERCHANT_TIME_OUT = 31;
    //集体试用时间
    Integer FIRM_DUE = 34;
    //黄金会员升级条件(个人业绩，团队业绩)
    Integer GOLD_MEMBER_CONDITION = 35;
    //黄金会员权限(集体直推，集体间推，市级直推，市级间推，分销直推，分销间推，拼团直推，拼团间推，秒杀直推，秒杀间推，优惠券直推，优惠券间推) 单位元
    Integer GOLD_MEMBER_AUTHO = 36;
    //合伙人升级条件(个人业绩，团队业绩)
    Integer PARTNER_CONDITION = 37;
    //合伙人权限(集体直推，集体间推，市级直推，市级间推，分销直推，分销间推，拼团直推，拼团间推，秒杀直推，秒杀间推，优惠券直推，优惠券间推) 单位元
    Integer PARTNER_AUTHO = 38;
    //至尊合伙人升级条件(个人业绩，团队业绩)
    Integer SUPER_PARTNER_CONDITION = 39;
    //至尊合伙人权限(集体直推，集体间推，市级直推，市级间推，分销直推，分销间推，拼团直推，拼团间推，秒杀直推，秒杀间推，优惠券直推，优惠券间推) 单位元
    Integer SUPER_PARTNER_AUTHO = 40;
    //至尊合伙人升级费用
    Integer SUPER_PARTNER_UPGRADE = 46;
    //平台交易抽成总比例(包含订单交易，同城推服务，商家自建分销服务费，广告服务费)
    Integer ADMIN_TRADING_DRAW = 47;

    //点赞加分
    Integer ADD_LIKES_SCORE = 48;
    //收藏加分
    Integer ADD_FAVOR_SCORE = 49;
    //评论加分
    Integer ADD_COMMENT_SCORE = 50;
    //分享加分
    Integer ADD_SHARE_SCORE = 51;
    //阅读加分
    Integer ADD_READ_SCORE = 52;

    //城市资讯多少小时后热度开始衰减(单位小时)
    Integer USER_SUB_HOUR = 53;
    //城市资讯 每小时衰减分数
    Integer USER_SUB_SCORE = 54;

    //普通会员分销应用直推佣金
    Integer MERCHANT_DIRECT_RECOMMEND = 55;
    //普通会员分销应用间推佣金
    Integer MERCHANT_INDIRECT_RECOMMEND = 56;

    //商品售后截止日期
    Integer AFTER_SELL_LIMIT = 57;
    //商家开通分销服务集体分成（除去集体和市级代理剩下的是平台）
    Integer MERCHANT_APP_FIRM = 58;
    //商家开通分销服务市级代理分成
    Integer MERCHANT_APP_CITY = 59;
    //平台手机号
    Integer CUSTOMER_SERVICE_PHONE = 60;

    //普通会员 （拼团直推，拼团间推，秒杀直推，秒杀间推，优惠券直推，优惠券间推）单位元
    Integer AUTHO = 61;
    //邀请入驻界面海报
    Integer POSTER = 62;

    //竞价协议
    Integer USER_BID_PROTOCOL = 63;

    //集体入驻 市级代理抽成（元）
    Integer CITY_FIRM_OPEN = 64;

    //集体提现到银行卡最低手续费
    Integer FIRM_COST_LIMIT = 66;
    //集体提现到银行卡手续费比例
    Integer FIRM_COST_PROPORTION = 67;
    //市级代理提现到银行卡最低手续费
    Integer CITY_COST_LIMIT = 68;
    //市级代理提现到银行卡手续费比例
    Integer CITY_COST_PROPORTION = 69;
    //普通用户提现到银行卡最低手续费
    Integer USER_COST_LIMIT = 70;
    //普通用户提现到银行卡手续费比例 (1 = 100%)微信手续费为金额的0.1%，最低1元，最高25元
    Integer USER_COST_PROPORTION = 71;
    //普通用户提现到微信零钱上限
    Integer USER_CASH_MONEY = 72;

    //vip价格
    Integer VIP_PRICE = 73;

    //城市vip
    Integer VIP_CITY = 74;

    //集体vip
    Integer VIP_MERCHANT = 75;

    //人脉VIP 服务协议类型
    Integer VIP_PROTOCOL = 76;

    //拼团
    Integer GROUP_ID = 1;
    //秒杀
    Integer SECKILL_ID = 2;
    //优惠券
    Integer COUPON_ID = 3;
    //分销
    Integer INVITE_ID = 4;
    //同城优选
    Integer CITY_PRO_SCORE = 6;

}