package com.leepsmart.code.common.liebao.service;

import com.leepsmart.code.system.pojo.SysLieBaoAccount;

import java.math.BigDecimal;
import java.util.List;

public interface LieBaoService {

    /**
     * 获取猎豹accessToken
     */
    String lieBaoAccessToken();

    /**
     * 生成OE开户链接
     */
    String OELink();

    /**
     * 获取账户列表
     */
    String fbAccountList();

    /**
     * 获取单个账户信息
     */
    String facebookAccountSingle(Long accountId);

    /**
     * 调整账户花费上限
     */
    boolean facebookAccountRecharge(String accountId, BigDecimal amount);

    /**
     * 账户绑定BM接口
     */
    boolean facebookAccountGrant(String accountId, String bmId, Integer type);

    /**
     * 查广告账户绑定的BM
     */
    String businessAccountBindings(String accountId);

    /**
     * 账户清零
     */
    boolean facebookAccountReset(String accountId);


}
