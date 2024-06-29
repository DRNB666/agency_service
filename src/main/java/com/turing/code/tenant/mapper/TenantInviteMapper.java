package com.turing.code.tenant.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.tenant.pojo.TenantInvite;
import com.turing.code.common.mybatisplus.methods.CommonMapper;
import com.turing.code.tenant.pojo.vo.TenantInviteVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author turing generator
 * @since 2024-06-01
 */
public interface TenantInviteMapper extends CommonMapper<TenantInvite> {

    List<Map<String, Object>> selectUserInviteShop(Map<String, Object> map);

    BigDecimal selectSumProAmountByAgentId(Long agentId, Long shopId);

    TenantInvite selectOneByMap(Map<String, Object> map);

    IPage<TenantInviteVO> selectByMapWithUser(Page<TenantInviteVO> page,@Param("map") Map<String, Object> map);
}
