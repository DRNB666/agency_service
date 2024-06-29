package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${cfg.packageName}.common.mybatisplus.methods.CommonServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends CommonServiceImpl<${table.mapperName}, ${entity}> implements ${table.serviceName} {

}
</#if>
