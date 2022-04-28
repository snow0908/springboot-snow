package com.snow.common.util;
/**
 * @author <a href="#">yanhua</a>
 * @version create on 2014年7月25日 上午12:46:30
 */
public abstract class EnumUtil {

    public static <E extends Enum<E>> E parseName(Class<E> enumType, String name) {
        if (enumType == null || StringUtil.isBlank(name)) {
            return null;
        }

        return Enum.valueOf(enumType, name);
    }

}

