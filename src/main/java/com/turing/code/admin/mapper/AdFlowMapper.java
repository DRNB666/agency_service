package com.turing.code.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.admin.pojo.AdFlow;
import com.turing.code.admin.pojo.vo.AdFlowVO;
import com.turing.code.common.mybatisplus.methods.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author turing generator
 * @since 2024-06-05
 */
public interface AdFlowMapper extends CommonMapper<AdFlow> {

    IPage<AdFlowVO> selectAdFlow(Page<AdFlowVO> page,@Param("map") Map<String, Object> map);
    List<AdFlowVO> selectAdFlow(@Param("map") Map<String, Object> map);
}
