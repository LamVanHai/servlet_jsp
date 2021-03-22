package com.lamvanhai.service.impl;

import com.lamvanhai.annotation.Autowire;
import com.lamvanhai.annotation.Service;
import com.lamvanhai.entity.Role;
import com.lamvanhai.mapper.RoleMapper;
import com.lamvanhai.model.response.role.RoleResponse;
import com.lamvanhai.repository.RoleRepository;
import com.lamvanhai.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowire
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleResponse> findAllRoleByUserId(long userId) {
        List<Role> roles = roleRepository.findRoleByUserId(userId);
        return roles
                .stream()
                .map(RoleMapper::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleResponse> findAll() {
        return roleRepository
                .findAll()
                .map(RoleMapper::convertToResponse)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {

    }
}







