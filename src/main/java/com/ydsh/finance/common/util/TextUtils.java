package com.ydsh.finance.common.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
* <p>
* 通用工具类
* </p>
* @author <a href="mailto:daiyihui@yidianlife.com">lk</a>
* @version V0.0.1
 */
public class TextUtils {

	/**
	 *
	 */
	public static String getMapByKeyToString(Map<String,Object> map,String key) {
		Object keyValue = map.get(key);
		if(keyValue==null||keyValue.toString().length()==0) {
			return null;
		}else {
			return keyValue.toString();
		}
	}
	/**
	 *  判断各种类型是否为空，为空返回true
	 * * @return
	 */
	public static boolean isEmpty(Object obj) {
		         if (obj == null) {return true;}
				 else if (obj instanceof String) {
					 return null==obj || "".equals(obj);
				 }
		         else if (obj instanceof CharSequence) {
					 return ((CharSequence) obj).length() == 0;
				 }
				 else if (obj instanceof Collection) {
					 return ((Collection) obj).isEmpty();
				 }
				 else if (obj instanceof Map) {
					 return ((Map) obj).isEmpty();
				 }
				 else if (obj.getClass().isArray()) {
					 return Array.getLength(obj) == 0;
				 }
		         return false;
		    }
	/**
	*  多参判断是否有参数为空
	* @return
	 */
	public static boolean isEmptys(Object... objs) {
		boolean mark = false;
		for (Object obj : objs) {
			if (isEmpty(obj)) {
				mark = true;
				return mark;
			}
		}
		return mark;
	}
    /**
     *  判断所有参数是否为空  true为全部为空
     * @return
     */
    public static boolean allEmptys(Object... objs) {
        boolean mark = true;
        for (Object obj : objs) {
            if (!isEmpty(obj)) {
                mark = false;
                return mark;
            }
        }
        return mark;
    }
    /**
     *  判断所有参数是否都不为空  true为全部都不为空
     * @return
     */
    public static boolean allNotEmptys(Object... objs) {
        boolean mark = true;
        for (Object obj : objs) {
            if (isEmpty(obj)) {
                mark = false;
                return mark;
            }
        }
        return mark;
    }
}
