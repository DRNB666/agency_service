package com.turing.code.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.code.common.ex.ServiceException;
import com.turing.code.common.utils.returnbody.ResultCodeInfo;
import com.turing.code.common.utils.returnbody.ReturnBody;
import com.turing.code.tenant.pojo.TenantPro;
import com.turing.code.tenant.pojo.TenantProCategory;
import com.turing.code.tenant.mapper.TenantProCategoryMapper;
import com.turing.code.tenant.service.TenantProCategoryService;
import com.turing.code.common.mybatisplus.methods.CommonServiceImpl;
import com.turing.code.tenant.service.TenantProService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author turing generator
 * @since 2024-06-06
 */
@Service
public class TenantProCategoryServiceImpl extends CommonServiceImpl<TenantProCategoryMapper, TenantProCategory> implements TenantProCategoryService {

    @Resource
    private TenantProService tenantProService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        //查出所有子分类
        QueryWrapper<TenantProCategory> eq = new QueryWrapper<TenantProCategory>().eq(TenantProCategory.PARENT_ID, id);
        List<TenantProCategory> list = list(eq);
        list.forEach(item -> {
            QueryWrapper<TenantPro> proEq = new QueryWrapper<TenantPro>().eq(TenantPro.CATEGORY_ID, item.getId());
            if (tenantProService.count(proEq) != 0) {
                throw new ServiceException("有商品绑定了该分类或该分类的子分类，无法删除");
            }
        });
        //删除当前分类
        if (!removeById(id)) {
            throw new ServiceException();
        }
        //删除所有子分类
        remove(eq);
    }
}
