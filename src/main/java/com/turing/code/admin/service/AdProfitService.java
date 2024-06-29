package com.turing.code.admin.service;

import com.turing.code.admin.pojo.AdProfit;
import com.turing.code.common.mybatisplus.methods.CommonService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author turing generator
 * @since 2021-02-03
 */
public interface AdProfitService extends CommonService<AdProfit> {

    BigDecimal selectSumByDay(Integer day);

    BigDecimal selectSumMoneyByDate(Map<String, String> map);

    List<Map<String, Object>> selectCountByDate(Map<String, Object> map);

}
