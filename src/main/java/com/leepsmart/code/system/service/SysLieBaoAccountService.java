package com.leepsmart.code.system.service;

import com.leepsmart.code.system.pojo.SysLieBaoAccount;
import com.leepsmart.code.common.mybatisplus.methods.CommonService;

import java.util.List;

/**
 * @author leepsmart generator
 * @since 2024-07-25
 */
public interface SysLieBaoAccountService extends CommonService<SysLieBaoAccount> {

    /**
     * 同步猎豹账户
     */
    void syncAccountList(List<SysLieBaoAccount> sysLieBaoAccounts);
}
