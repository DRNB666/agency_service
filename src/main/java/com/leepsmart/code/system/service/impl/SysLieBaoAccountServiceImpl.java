package com.leepsmart.code.system.service.impl;

import com.leepsmart.code.common.ex.ServiceException;
import com.leepsmart.code.system.pojo.SysLieBaoAccount;
import com.leepsmart.code.system.mapper.SysLieBaoAccountMapper;
import com.leepsmart.code.system.service.SysLieBaoAccountService;
import com.leepsmart.code.common.mybatisplus.methods.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author leepsmart generator
 * @since 2024-07-25
 */
@Service
public class SysLieBaoAccountServiceImpl extends CommonServiceImpl<SysLieBaoAccountMapper, SysLieBaoAccount> implements SysLieBaoAccountService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncAccountList(List<SysLieBaoAccount> sysLieBaoAccounts) {
        if (!saveBatch(sysLieBaoAccounts)) {
            throw new ServiceException();
        }
    }
}
