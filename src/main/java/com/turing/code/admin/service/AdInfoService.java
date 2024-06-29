package com.turing.code.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.admin.pojo.AdInfo;
import com.turing.code.admin.pojo.vo.AdInfoVO;
import com.turing.code.common.mybatisplus.methods.CommonService;

/**
 * @author turing generator
 * @since 2021-02-03
 */
public interface AdInfoService extends CommonService<AdInfo> {

    String authority(String account, String password);

    IPage<AdInfoVO> selectAndRole(Page page, QueryWrapper<AdInfoVO> queryWrapper);

    String add(AdInfo adInfo);

    String delete(Integer id);

    String updateInfo(AdInfo adInfo);
}