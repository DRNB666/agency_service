package com.turing.code.admin.service;

import com.turing.code.admin.pojo.AdFlow;
import com.turing.code.common.mybatisplus.methods.CommonService;
import com.turing.code.common.utils.page.pojo.PageInfo;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author turing generator
 * @since 2024-06-05
 */
public interface AdFlowService extends CommonService<AdFlow> {

    String selectRewardFlow(PageInfo pageInfo, Map<String, Object> map);

    String selectRewardFlowExport(Map<String, Object> map);

    String selectAdFlowExport(Map<String, Object> map);

    void addWxServiceMoney(BigDecimal money, Long promoterAuditId, int i, int i1);
}
