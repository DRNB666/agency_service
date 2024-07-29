package com.leepsmart.code.common.mybatisplus.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.leepsmart.code.common.mybatisplus.sqltemplate.SelectByIdForUpdate;
import com.leepsmart.code.common.mybatisplus.sqltemplate.SelectListForUpdate;
import com.leepsmart.code.common.mybatisplus.sqltemplate.SelectOneForUpdate;

import java.util.List;

/**
 * sql注入器
 */
public class CustomerSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new SelectOneForUpdate());
        methodList.add(new SelectListForUpdate());
        methodList.add(new SelectByIdForUpdate());
        return methodList;
    }
}
