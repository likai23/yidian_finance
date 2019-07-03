package com.ydsh.finance.common.mybatisPlusAddFunction.mySqlInject;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.session.Configuration;

public class MySqlInjector implements ISqlInjector {
    @Override
    public void inspectInject(MapperBuilderAssistant builderAssistant, Class<?> mapperClass) {

    }

    @Override
    public void injectSqlRunner(Configuration configuration) {

    }
}
