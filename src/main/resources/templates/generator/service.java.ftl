package ${package.Service};

import ${package.Entity}.${entity};
import ${cfg.packageName}.common.mybatisplus.methods.CommonService;

/**
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends CommonService<${entity}> {

}
</#if>
