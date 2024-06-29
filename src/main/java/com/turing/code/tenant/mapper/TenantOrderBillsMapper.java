package com.turing.code.tenant.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.tenant.pojo.TenantOrderBills;
import com.turing.code.common.mybatisplus.methods.CommonMapper;
import com.turing.code.tenant.pojo.vo.TenantBillsConfirmVo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * @author turing generator
 * @since 2024-06-14
 */
public interface TenantOrderBillsMapper extends CommonMapper<TenantOrderBills> {

    IPage<TenantBillsConfirmVo> confirmList(Page<TenantBillsConfirmVo> page,@Param("param") HashMap<String, Object> stringObjectHashMap);
}
