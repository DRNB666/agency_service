package com.turing.code.user.service;

import com.turing.code.user.pojo.UserWithdraw;
import com.turing.code.common.mybatisplus.methods.CommonService;

/**
 * @author turing generator
 * @since 2024-06-04
 */
public interface UserWithdrawService extends CommonService<UserWithdraw> {

    /**
     * 提现
     */
    String withdraw(UserWithdraw userWithdraw);
}
