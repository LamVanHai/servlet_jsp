package com.lamvanhai.repository.impl;

import com.lamvanhai.annotation.Repository;
import com.lamvanhai.entity.User;
import com.lamvanhai.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.lamvanhai.util.ReflectionUtil.convertToEntity;

@Repository
public class UserRepositoryImpl extends BaseQuery<User, Long> implements UserRepository {
    @Override
    public Optional<User> findUserByUserNameAndPassword(String userName, String password) {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, userName);
            ps.setObject(2, password);

            ResultSet resultSet = ps.executeQuery();
            User user = null;

            while (resultSet.next()) {
                user = convertToEntity(resultSet, User.class);
            }

            return Optional.of(user);
        } catch (SQLException | IllegalAccessException | InstantiationException | NoSuchFieldException throwables) {
            throwables.printStackTrace();

            return Optional.empty();
        }


    }
}
