package com.turing.code.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.user.pojo.UserRoyalFlow;
import com.turing.code.common.mybatisplus.methods.CommonMapper;
import com.turing.code.user.pojo.UserRoyalFlowPro;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author turing generator
 * @since 2024-05-31
 */
public interface UserRoyalFlowMapper extends CommonMapper<UserRoyalFlow> {

    int selectCountSelective(UserRoyalFlow build);

    BigDecimal selectSumMoneyByUserId(Map<String, Object> map);
    List<UserRoyalFlow> selectSelective(UserRoyalFlow record);
    IPage<Map<String, Object>> selectMap(Page<Map<String, Object>> page, @Param("map") Map<String, Object> map);


}
