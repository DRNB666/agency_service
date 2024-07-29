package com.leepsmart.code.admin.service;

import com.leepsmart.code.admin.pojo.AdRole;
import com.leepsmart.code.common.mybatisplus.methods.CommonService;

/**
 * @author leepsmart generator
 * @since 2021-02-03
 */
public interface AdRoleService extends CommonService<AdRole> {

    String delete(Integer id);

}
