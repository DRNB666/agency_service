package com.turing.code.tenant.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.tenant.pojo.TenantPro;
import com.turing.code.common.mybatisplus.methods.CommonMapper;
import com.turing.code.user.pojo.vo.MerchantProInvite;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author turing generator
 * @since 2024-06-01
 */
public interface TenantProMapper extends CommonMapper<TenantPro> {

    List<MerchantProInvite> selectMerchantProInviteList(@Param("map") Map<String, Object> map);

    IPage<TenantPro> selectByMapEx(Page<TenantPro> page,@Param("map") Map<String, Object> map);

    BigDecimal highPriceSku(@Param("id") Long id);
}
