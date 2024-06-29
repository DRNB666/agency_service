package com.turing.code.tenant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.tenant.pojo.TenantOrderBills;
import com.turing.code.common.mybatisplus.methods.CommonService;
import com.turing.code.tenant.pojo.vo.TenantBillsConfirmVo;

import java.util.HashMap;

/**
 * @author turing generator
 * @since 2024-06-14
 */
public interface TenantOrderBillsService extends CommonService<TenantOrderBills> {

    /**
     * 收款确认列表
     */
    IPage<TenantBillsConfirmVo> confirmList(Page<TenantBillsConfirmVo> page, HashMap<String, Object> stringObjectHashMap);

    /**
     * 审核收款
     */
    void auditCollection(TenantOrderBills tenantOrderBills);
}
