package com.utils.enumUtils;

/**
 * Created by oksdud on 01.12.2016.
 */
public class EnumUtil {

    static <E extends Enum<E>> Enum getEnumValue(String what, Class<? extends Enum> enumClass) {
        Enum<E> unknown = null;
        for (Enum<E> enumVal : enumClass.getEnumConstants()) {
            if (what.compareToIgnoreCase(enumVal.name()) == 0) {
                return enumVal;
            }
            if (enumVal.name().compareToIgnoreCase("unknown") == 0) {
                unknown = enumVal;
            }
        }
        return unknown;
    }

}
