package com.lamvanhai.util;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static com.lamvanhai.util.AnnotationUtil.getColumnName;
import static com.lamvanhai.util.AnnotationUtil.getPrimaryKey;

public class ReflectionUtil {

    public static Object get(Object object, Field fieldName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String getFieldName = "get" + fieldName.getName().substring(0, 1).toUpperCase() + fieldName.getName().substring(1);
        Method method = object.getClass().getMethod(getFieldName);
        return method.invoke(object);
    }

    public static void set(Object object, Field field, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(object, value);
    }

    public static <T> T convertToEntity(ResultSet rs, Class<T> tClass) throws IllegalAccessException, InstantiationException, NoSuchFieldException, SQLException {
        T newObject = tClass.newInstance();
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("id")) {
                set(newObject, field, rs.getObject(getPrimaryKey(tClass, field.getName())));
                continue;
            }
            if (field.getType().getTypeName().equals("java.time.LocalDate")) {
                set(newObject, field, ((Timestamp) rs.getObject(getColumnName(tClass, field.getName()))).toLocalDateTime().toLocalDate());
            } else if (field.getType().getTypeName().equals("java.time.LocalDateTime")) {
                set(newObject, field, ((Timestamp) rs.getObject(getColumnName(tClass, field.getName()))).toLocalDateTime());
            } else {
                set(newObject, field, rs.getObject(getColumnName(tClass, field.getName())));
            }
        }
        return newObject;
    }

    public static <T, P> void mapper(T source, P target) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Field[] sourceFields = source.getClass().getDeclaredFields();
        Field[] targetFields = target.getClass().getDeclaredFields();
        for (int i = 0; i < sourceFields.length; i++) {
            Field sourceField = sourceFields[i];
            if (get(source, sourceField) == null) {
                continue;
            }
            for (int j = 0; j < targetFields.length; j++) {
                Field targetField = targetFields[j];
                if (sourceField.getName().equals(targetField.getName())
                        && sourceField.getType().getTypeName().equals(targetField.getType().getTypeName())) {
                    targetField.setAccessible(true);
                    targetField.set(target, get(source, sourceField));
                    continue;
                }
            }
        }
    }
     public static <T,P> void mapperList(List<T> source,List<P> target){

     }

}
