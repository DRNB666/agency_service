package com.turing.code.system.mapper;

import com.turing.code.system.pojo.SysBank;
import com.turing.code.common.mybatisplus.methods.CommonMapper;

import java.util.List;

/**
 * @author turing generator
 * @since 2024-06-04
 */
public interface SysBankMapper extends CommonMapper<SysBank> {

    List<SysBank> selectSelective(SysBank sysBank);
}
