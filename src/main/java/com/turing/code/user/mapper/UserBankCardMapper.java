package com.turing.code.user.mapper;

import com.turing.code.user.pojo.UserBankCard;
import com.turing.code.common.mybatisplus.methods.CommonMapper;

import java.util.List;

/**
 * @author turing generator
 * @since 2024-06-04
 */
public interface UserBankCardMapper extends CommonMapper<UserBankCard> {

    int selectCountSelective(UserBankCard userBankCard);

    List<UserBankCard> selectSelective(UserBankCard userBankCard);
}
