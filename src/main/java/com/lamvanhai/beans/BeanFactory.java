package com.lamvanhai.beans;

import com.lamvanhai.annotation.Autowire;
import com.lamvanhai.annotation.Component;
import com.lamvanhai.annotation.Repository;
import com.lamvanhai.annotation.Service;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {
    public static ConcurrentHashMap<String, Object> beans = new ConcurrentHashMap<>();

    static {
        Reflections reflections = new Reflections("com.daoxuanson.shop");
        Set<Class<?>> repositoriesClasses = reflections.getTypesAnnotatedWith(Repository.class);
        Set<Class<?>> serviceClasses = reflections.getTypesAnnotatedWith(Service.class);
        Set<Class<?>> componentClasses = reflections.getTypesAnnotatedWith(Component.class);
        try {
            putBean(repositoriesClasses);
            putBean(serviceClasses);
            putBean(componentClasses);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    private static void putBean(Set<Class<?>> classes) throws IllegalAccessException, InstantiationException {
        if (classes.isEmpty()) return;

        for (Class clazz : classes) {
            String key = getKey(clazz);
            Field[] fields = clazz.getDeclaredFields();
            if (fields.length == 0) {
                try {
                    Object object = clazz.newInstance();
                    beans.put(key, object);
                    continue;
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            List<Class> types = new ArrayList<>();
            List<Object> values = new ArrayList<>();

            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowire.class)) {
                    types.add(field.getType());
                    Object bean = beans.get(field.getName());
                    values.add(bean);
                }
            }

            Class[] parameters = new Class[types.size()];
            int i = 0;

            for (Class type : types) {
                parameters[i++] = type;
            }

            try {
                Constructor constructor = clazz.getConstructor(parameters);
                Object object = constructor.newInstance(values.toArray());
                beans.put(key, object);

            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }


    private static String getKey(Class<?> bean) {
        String name = bean.getSimpleName();
        if (name.endsWith("Impl")) {
            name = name.substring(0, name.indexOf("Impl"));
            name = name.substring(0, 1).toLowerCase() + name.substring(1);
        } else {//userMapper
            name = name.substring(0, 1).toLowerCase() + name.substring(1);
        }

        return name;
    }
}
