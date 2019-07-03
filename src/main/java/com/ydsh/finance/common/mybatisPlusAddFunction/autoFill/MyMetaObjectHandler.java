package com.ydsh.finance.common.mybatisPlusAddFunction.autoFill;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自定义公共字段填充处理器
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

//    private static final Logger LOGGER = LoggerFactory.getLogger(MyMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
//        LOGGER.info("start insert fill ....");
        this.setFieldValByName("createTime", new Date(), metaObject);
//        this.setFieldValByName("createId", 23, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
//        LOGGER.info("start update fill ....");
        this.setFieldValByName("createTime", new Date(), metaObject);
//        this.setFieldValByName("createId", 23, metaObject);
        //this.setUpdateFieldValByName("operator", "Tom", metaObject);//@since 快照：3.0.7.2-SNAPSHOT， @since 正式版暂未发布3.0.7
    }
}
