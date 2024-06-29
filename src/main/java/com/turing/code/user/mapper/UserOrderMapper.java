package com.turing.code.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.user.pojo.UserOrder;
import com.turing.code.common.mybatisplus.methods.CommonMapper;

/**
 * @author turing generator
 * @since 2024-06-13
 */
public interface UserOrderMapper extends CommonMapper<UserOrder> {

    IPage<UserOrder> collectionProgressList(Page<UserOrder> page, Integer tenantId, Integer orderId, String phone, Integer receiptStatus);
}
