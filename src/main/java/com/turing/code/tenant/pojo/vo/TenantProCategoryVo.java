package com.turing.code.tenant.pojo.vo;

import com.turing.code.tenant.pojo.TenantProCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TenantProCategoryVo extends TenantProCategory {
    //是否有子分类
    private Boolean hasChildren;
}
