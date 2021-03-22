package com.lamvanhai.util;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

public class BeanUtil {

    public static <T> T toModel(Class<T> tClass, HttpServletRequest request) {
        try {
            T model = tClass.newInstance();
            BeanUtils.populate(model, request.getParameterMap());
            return model;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();

            return null;
        }

    }


}
