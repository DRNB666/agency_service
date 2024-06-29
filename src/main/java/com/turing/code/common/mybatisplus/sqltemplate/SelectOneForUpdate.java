package com.turing.code.common.mybatisplus.sqltemplate;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 单数据悲观锁查询
 */
public class SelectOneForUpdate extends AbstractMethod {

    private static final String MAPPER_METHOD = "selectOneForUpdate";
    private static final String SQL_TEMPLATE = "<script>%s SELECT %s FROM %s %s %s for update\n</script>";

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql = String.format(
                SQL_TEMPLATE,
                sqlFirst(),
                sqlSelectColumns(tableInfo, true),
                tableInfo.getTableName(),
                sqlWhereEntityWrapper(true, tableInfo),
                sqlComment());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForTable(mapperClass, MAPPER_METHOD, sqlSource, tableInfo);
    }
}
