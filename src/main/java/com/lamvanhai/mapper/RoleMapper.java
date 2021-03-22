package com.lamvanhai.mapper;

import com.lamvanhai.entity.Role;
import com.lamvanhai.model.response.role.RoleResponse;
import com.lamvanhai.util.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;

public class RoleMapper {

    public static RoleResponse convertToResponse(Role role) {
        RoleResponse roleResponse = new RoleResponse();
        try {
            ReflectionUtil.mapper(role, roleResponse);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return roleResponse;
    }
}
