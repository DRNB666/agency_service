package com.turing.code.tenant.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.tenant.pojo.TenantInfoFlowing;
import com.turing.code.common.mybatisplus.methods.CommonMapper;
import com.turing.code.tenant.pojo.vo.UserRoyalFlowProVO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author turing generator
 * @since 2024-06-05
 */
public interface TenantInfoFlowingMapper extends CommonMapper<TenantInfoFlowing> {

    IPage<UserRoyalFlowProVO> selectVoByMap(Page<UserRoyalFlowProVO> page,@Param("map") Map<String, Object> map);
}
