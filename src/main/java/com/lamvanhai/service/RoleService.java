package com.lamvanhai.service;

import com.lamvanhai.model.response.role.RoleResponse;

import java.util.List;

public interface RoleService {
    List<RoleResponse> findAllRoleByUserId(long userId);

    List<RoleResponse> findAll();
}
