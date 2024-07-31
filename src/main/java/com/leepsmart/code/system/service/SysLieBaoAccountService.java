package com.leepsmart.code.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leepsmart.code.system.pojo.SysLieBaoAccount;
import com.leepsmart.code.common.mybatisplus.methods.CommonService;

import java.util.HashMap;
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

    /**
     * 获取猎豹账户列表
     */
    IPage<SysLieBaoAccount> accountList(Page<SysLieBaoAccount> page, HashMap<String, Object> hashMap);
}
