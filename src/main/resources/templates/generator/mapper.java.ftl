package ${package.Mapper};

import ${package.Entity}.${entity};
import ${cfg.packageName}.common.mybatisplus.methods.CommonMapper;

/**
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends CommonMapper<${entity}> {

}
</#if>
