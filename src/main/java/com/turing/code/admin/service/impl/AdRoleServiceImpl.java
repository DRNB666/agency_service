package com.turing.code.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.code.admin.mapper.AdInfoMapper;
import com.turing.code.admin.mapper.AdRoleMapper;
import com.turing.code.admin.pojo.AdInfo;
import com.turing.code.admin.pojo.AdRole;
import com.turing.code.admin.service.AdRoleService;
import com.turing.code.common.ex.ServiceException;
import com.turing.code.common.mybatisplus.methods.CommonServiceImpl;
import com.turing.code.common.mybatisplus.structure.CustomQueryWrapper;
import com.turing.code.common.utils.returnbody.ReturnBody;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author turing generator
 * @since 2021-02-03
 */
@Service
public class AdRoleServiceImpl extends CommonServiceImpl<AdRoleMapper, AdRole> implements AdRoleService {

    @Resource
    private AdInfoMapper adInfoMapper;

    @Transactional
    @Override
    public String delete(Integer id) {
        AdRole adRole = super.baseMapper.selectById(id);
        // 删除角色
        if (super.baseMapper.deleteById(id) != 1) {
            throw new ServiceException("删除失败");
        }
        // 删除角色下边所有账号
        if (adRole.getAccountNum() > 0) {
            QueryWrapper<AdInfo> queryWrapper = new CustomQueryWrapper<>();
            queryWrapper.eq(AdInfo.ROLE_ID, id);
            if (adInfoMapper.delete(queryWrapper) != adRole.getAccountNum()) {
                throw new ServiceException("删除账号失败");
            }
        }
        return ReturnBody.success();
    }

}