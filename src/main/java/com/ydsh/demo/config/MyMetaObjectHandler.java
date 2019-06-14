package com.ydsh.demo.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 自定义公共字段填充处理器
 */
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入操作自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //获取到需要被填充的字段的值
        Object fieldValue = getFieldValByName("name", metaObject);
        if(fieldValue == null) {
            System.out.println("*******插入操作 满足填充条件*********");
            //新增时填充的字段
            setFieldValByName("create_time", new Date(), metaObject);
            setFieldValByName("review_time", new Date(), metaObject);
        }
    }
    /**
     * 修改操作自动填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Object fieldValue = getFieldValByName("name", metaObject);
        if(fieldValue == null) {
            System.out.println("*******修改操作 满足填充条件*********");
            setFieldValByName("update_time", new Date(), metaObject);
        }
    }
}

