package com.ydsh.finance.common.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
public class MyObjectUtils {

    private MyObjectUtils() {
    }

    /**
     * 判断类中每个属性是否都为空
     *
     * @param o
     * @return
     */
    public static boolean allFieldIsNULL(Object o){
        try {
            for (Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                Object object = field.get(o);
                if (object instanceof CharSequence) {
                    if (!org.springframework.util.ObjectUtils.isEmpty(object)) {
                        return false;
                    }
                } else {
                    if (null != object) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            log.error("判断对象属性为空异常", e);

        }
        return true;
    }

}

