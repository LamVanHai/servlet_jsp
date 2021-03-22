package com.lamvanhai.repository.impl;

import com.lamvanhai.annotation.Repository;
import com.lamvanhai.entity.Role;
import com.lamvanhai.repository.RoleRepository;
import com.lamvanhai.util.ReflectionUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleRepositoryImpl extends BaseQuery<Role, Long> implements RoleRepository {


    @Override
    public List<Role> findRoleByUserId(long userId) {
        String sql = "SELECT r.* FROM role r join permission p on r.id = p.role_id WHERE p.user_id = ?";
        List<Role> roles = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, userId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Role role = ReflectionUtil.convertToEntity(resultSet, Role.class);
                roles.add(role);
            }
        } catch (SQLException | IllegalAccessException | InstantiationException | NoSuchFieldException throwables) {
            throwables.printStackTrace();
        }

        return roles;
    }
}
