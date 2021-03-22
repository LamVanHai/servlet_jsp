package com.lamvanhai.util;

import com.lamvanhai.annotation.Column;
import com.lamvanhai.annotation.Entity;
import com.lamvanhai.annotation.Id;

public class AnnotationUtil {

    public static String getTableName(Class<?> tClass) {
        return tClass.getAnnotation(Entity.class).name();
    }

    public static String getColumnName(Class<?> tClass, String fieldName) throws NoSuchFieldException {
        return tClass.getDeclaredField(fieldName).getAnnotation(Column.class).name();
    }

    public static String getPrimaryKey(Class<?> tClass, String fieldName) throws NoSuchFieldException {
        return tClass.getDeclaredField(fieldName).getAnnotation(Id.class).name();
    }

    public static boolean getAutoIncrement(Class<?> tClass, String fieldName) throws NoSuchFieldException {
        return tClass.getDeclaredField(fieldName).getAnnotation(Id.class).increment();
    }
}
