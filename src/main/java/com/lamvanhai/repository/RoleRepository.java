package com.lamvanhai.repository;

import com.lamvanhai.entity.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findRoleByUserId(long userId);

}
