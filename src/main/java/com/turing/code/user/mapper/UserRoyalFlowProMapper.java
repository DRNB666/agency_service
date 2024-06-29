package com.turing.code.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.tenant.pojo.vo.UserRoyalFlowProVO;
import com.turing.code.user.pojo.UserRoyalFlowPro;
import com.turing.code.common.mybatisplus.methods.CommonMapper;
import com.turing.code.user.pojo.vo.UserRoyalFlowVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author turing generator
 * @since 2024-05-31
 */
public interface UserRoyalFlowProMapper extends CommonMapper<UserRoyalFlowPro> {

    IPage<UserRoyalFlowPro> selectByMapEx(Page<UserRoyalFlowPro> page, @Param("map") Map<String, Object> map);

    IPage<UserRoyalFlowVO> selectFlowWithAllByMap(Page<UserRoyalFlowVO> page, @Param("map") Map<String, Object> map);

    IPage<UserRoyalFlowProVO> selectFlowProWithAllByMap(Page<UserRoyalFlowProVO> page, @Param("map") Map<String, Object> map);

    List<UserRoyalFlowProVO> selectFlowProWithAllByMap(@Param("map") Map<String, Object> map);
}
