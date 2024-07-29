package com.leepsmart.code.admin.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leepsmart.code.admin.pojo.AdInfo;
import com.leepsmart.code.admin.pojo.vo.AdInfoVO;
import com.leepsmart.code.common.mybatisplus.methods.CommonMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author leepsmart generator
 * @since 2021-02-03
 */
public interface AdInfoMapper extends CommonMapper<AdInfo> {

    IPage<AdInfoVO> selectAndRole(Page<AdInfoVO> page, @Param("ew") QueryWrapper<AdInfoVO> queryWrapper);

}
