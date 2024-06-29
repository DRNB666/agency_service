package com.turing.code.common.mybatisplus.structure;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.turing.code.common.utils.CommUtil;

import java.util.Collection;

/**
 * 定制 UpdateWrapper
 *
 * @author Turing
 */
public class CustomUpdateWrapper<T> extends UpdateWrapper<T> {

    @Override
    protected UpdateWrapper<T> addCondition(boolean condition, String column, SqlKeyword sqlKeyword, Object val) {
        // 忽略条件参数值为空的条件
        if (!CommUtil.checkNull(val)) {
            condition = false;
        }
        return super.addCondition(condition, column, sqlKeyword, val);
    }

    @Override
    protected UpdateWrapper<T> likeValue(boolean condition, SqlKeyword keyword, String column, Object val, SqlLike sqlLike) {
        if (!CommUtil.checkNull(val)) {
            condition = false;
        }
        return super.likeValue(condition, keyword, column, val, sqlLike);
    }

    @Override
    public UpdateWrapper<T> in(boolean condition, String column, Collection<?> coll) {
        if (coll.size() < 1) {
            condition = false;
        }
        return super.in(condition, column, coll);
    }

    @Override
    public UpdateWrapper<T> notIn(boolean condition, String column, Collection<?> coll) {
        if (coll.size() < 1) {
            condition = false;
        }
        return super.notIn(condition, column, coll);
    }
}
