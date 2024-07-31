package com.leepsmart.code.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leepsmart.code.system.pojo.SysLieBaoAccount;
import com.leepsmart.code.common.mybatisplus.methods.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * @author leepsmart generator
 * @since 2024-07-25
 */
public interface SysLieBaoAccountMapper extends CommonMapper<SysLieBaoAccount> {

    /**
     * 获取猎豹账户列表
     */
    IPage<SysLieBaoAccount> accountList(Page<SysLieBaoAccount> page,@Param("map") HashMap<String, Object> hashMap);
}
