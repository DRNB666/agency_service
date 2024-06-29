package com.turing.code.user.service;

import com.turing.code.common.utils.page.pojo.PageInfo;
import com.turing.code.user.pojo.UserRoyalFlow;
import com.turing.code.common.mybatisplus.methods.CommonService;

import java.util.Map;

/**
 * @author turing generator
 * @since 2024-05-31
 */
public interface UserRoyalFlowService extends CommonService<UserRoyalFlow> {

    String selectFlowWithAllByMap(PageInfo pageInfo, Map<String, Object> map);
}
