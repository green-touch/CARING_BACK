package com.caring.manager_service.common.util;

import com.caring.manager_service.common.interfaces.KeyedEnum;

import java.util.Arrays;

public class EnumConvertUtil {

    public static <E extends Enum<E> & KeyedEnum> E convert(Class<E> enumClass, String key) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> e.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Key not found in " + enumClass.getSimpleName()));
    }
}
