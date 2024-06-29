package com.turing.code.admin.service.impl;

import com.turing.code.admin.mapper.AdProfitMapper;
import com.turing.code.admin.pojo.AdProfit;
import com.turing.code.admin.service.AdProfitService;
import com.turing.code.common.mybatisplus.methods.CommonServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author turing generator
 * @since 2021-02-03
 */
@Service
public class AdProfitServiceImpl extends CommonServiceImpl<AdProfitMapper, AdProfit> implements AdProfitService {

    @Override
    public BigDecimal selectSumByDay(Integer day) {
        return super.baseMapper.selectSumByDay(day);
    }

    @Override
    public BigDecimal selectSumMoneyByDate(Map<String, String> map) {
        return super.baseMapper.selectSumMoneyByDate(map);
    }

    @Override
    public List<Map<String, Object>> selectCountByDate(Map<String, Object> map) {
        return super.baseMapper.selectCountByDate(map);
    }

}
