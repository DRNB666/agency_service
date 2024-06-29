package com.turing.code.common.mybatisplus.structure;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.turing.code.common.utils.CommUtil;

import java.util.Collection;

/**
 * 定制 QueryWrapper
 *
 * @author Turing
 */
public class CustomQueryWrapper<T> extends QueryWrapper<T> {

    @Override
    protected QueryWrapper<T> addCondition(boolean condition, String column, SqlKeyword sqlKeyword, Object val) {
        // 忽略条件参数值为空的条件
        if (!CommUtil.checkNull(val)) {
            condition = false;
        }
        return super.addCondition(condition, column, sqlKeyword, val);
    }

    @Override
    protected QueryWrapper<T> likeValue(boolean condition, SqlKeyword keyword, String column, Object val, SqlLike sqlLike) {
        if (!CommUtil.checkNull(val)) {
            condition = false;
        }
        return super.likeValue(condition, keyword, column, val, sqlLike);
    }

    @Override
    public QueryWrapper<T> in(boolean condition, String column, Collection<?> coll) {
        if (coll.size() < 1) {
            condition = false;
        }
        return super.in(condition, column, coll);
    }

    @Override
    public QueryWrapper<T> notIn(boolean condition, String column, Collection<?> coll) {
        if (coll.size() < 1) {
            condition = false;
        }
        return super.notIn(condition, column, coll);
    }
}
