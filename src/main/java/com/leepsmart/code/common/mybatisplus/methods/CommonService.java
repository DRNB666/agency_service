package com.leepsmart.code.common.mybatisplus.methods;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;

/**
 * @author leepsmart
 */
public interface CommonService<T> extends IService<T> {

    T getOneForUpdate(Wrapper<T> queryWrapper);

    List<T> listForUpdate(Wrapper<T> queryWrapper);

    T getByIdForUpdate(Serializable id);

}
