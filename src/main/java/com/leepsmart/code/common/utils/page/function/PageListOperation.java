package com.leepsmart.code.common.utils.page.function;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 分页数据执行函数
 *
 * @param <T> 分页列表数据类型
 * @author leepsmart
 */
@FunctionalInterface
public interface PageListOperation<T> {

    void run(Page<T> page, QueryWrapper<T> queryWrapper);
}
