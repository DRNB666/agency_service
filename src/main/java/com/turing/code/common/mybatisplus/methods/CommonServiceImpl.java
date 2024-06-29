package com.turing.code.common.mybatisplus.methods;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.List;

/**
 * serviceImpl 扩展
 *
 * @param <M>
 * @param <T>
 */
public class CommonServiceImpl<M extends CommonMapper<T>, T> extends ServiceImpl<M, T> {

    public T getOneForUpdate(Wrapper<T> queryWrapper) {
        return super.getBaseMapper().selectOneForUpdate(queryWrapper);
    }

    public List<T> listForUpdate(Wrapper<T> queryWrapper) {
        return super.getBaseMapper().selectListForUpdate(queryWrapper);
    }

    public T getByIdForUpdate(Serializable id) {
        return super.getBaseMapper().selectByIdForUpdate(id);
    }

}
