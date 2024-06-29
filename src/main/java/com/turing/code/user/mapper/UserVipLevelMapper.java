package com.turing.code.user.mapper;

import com.turing.code.user.pojo.UserVipLevel;
import com.turing.code.common.mybatisplus.methods.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @author turing generator
 * @since 2024-06-23
 */
public interface UserVipLevelMapper extends CommonMapper<UserVipLevel> {

    BigDecimal highestDiscount(@Param("param") HashMap<String, Object> stringObjectHashMap);
}
