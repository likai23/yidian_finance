package com.ydsh.finance.common.util;

import com.baomidou.mybatisplus.annotation.TableField;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * map转bean，bean转map
 * </p>
 *
 * @author <a href="mailto:yangyanrui@yidianlife.com">xiaoyang</a>
 * @version V0.0.1
 * @date 2019年06月14日
 */
public class MapBeanUtil {

    /**
     * 实体对象转成Map
     *
     * @param obj 实体对象
     * @return
     */
    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Map转成实体对象
     *
     * @param map   实体对象包含属性
     * @param clazz 实体对象类型
     * @return
     */
    public static Object map2Object(Map<String, Object> map, Class<?> clazz) {
        if (map == null) {
            return null;
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * @param obj
     * @return
     */
    public static Map<String, Object> objectCamel2MapUnderline(Object obj) {
        Class<? extends Object> clazz = obj.getClass();
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                try {
                    boolean flag = field.isAnnotationPresent(TableField.class);
                    if (flag) {
                        TableField tableField = field.getAnnotation(TableField.class);
                        if (!tableField.exist()) {
                            continue;
                        }
                    }
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                    Method method = pd.getReadMethod();
                    Object dstObject = method.invoke(obj);
                    if (dstObject == null || "".equals(dstObject)) {
                        continue;
                    }
                    if (dstObject instanceof Date) {
                        map.put(com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(field.getName()), (Date) dstObject);
                        continue;
                    }
                    if (dstObject instanceof Integer) {
                        map.put(com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(field.getName()), (Integer) dstObject);
                        continue;
                    }
                    if (dstObject instanceof Long) {
                        map.put(com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(field.getName()), (Long) dstObject);
                        continue;
                    }
                    if (dstObject instanceof String) {
                        map.put(com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(field.getName()), (String) dstObject);
                        continue;
                    }
                    map.put(com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(field.getName()), dstObject);
                } catch (IntrospectionException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
}
