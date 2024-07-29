package com.leepsmart.code.common.mybatisplus.sqltemplate;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author leepsmart
 */
public class SelectByIdForUpdate extends AbstractMethod {

    private static final String MAPPER_METHOD = "selectByIdForUpdate";
    private static final String SQL_TEMPLATE = "<script>SELECT %s FROM %s where (%s = #{id}) for update\n</script>";

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql = String.format(
                SQL_TEMPLATE,
                tableInfo.getAllSqlSelect(),
                tableInfo.getTableName(),
                tableInfo.getKeyColumn());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForTable(mapperClass, MAPPER_METHOD, sqlSource, tableInfo);
    }
}
