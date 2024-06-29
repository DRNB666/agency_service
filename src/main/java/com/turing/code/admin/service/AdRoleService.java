package com.turing.code.admin.service;

import com.turing.code.admin.pojo.AdRole;
import com.turing.code.common.mybatisplus.methods.CommonService;

/**
 * @author turing generator
 * @since 2021-02-03
 */
public interface AdRoleService extends CommonService<AdRole> {

    String delete(Integer id);

}
