package com.ydsh.finance.common.mybatisPlusAddFunction.mySqlInject;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.ydsh.finance.common.mybatisPlusAddFunction.mySqlInject.MySqlEnum.MySqlEnum;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

public class MySqlInject extends AbstractMethod {
//    @Override
//    public List<AbstractMethod> getMethodList() {
//        return null;
//    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        //得到待解析sql的模板
        MySqlEnum fuzzyQueryByPage = MySqlEnum.FuzzyQuery_By_Page;
        //利用语言驱动和配置信息，table元数据，和刚才得到的sql方法模板得到sqlSource
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, String.format(fuzzyQueryByPage.getSql(),
                tableInfo.getTableName(), tableInfo.getKeyColumn(), tableInfo.getKeyProperty()), modelClass);
        //最后添加到mybatis的configuration里的mappedStatements中

        return this.addDeleteMappedStatement(mapperClass, fuzzyQueryByPage.getMethod(), sqlSource);
    }
}
