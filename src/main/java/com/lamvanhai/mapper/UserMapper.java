package com.lamvanhai.mapper;

import com.lamvanhai.entity.User;
import com.lamvanhai.model.request.user.UserSaveRequest;
import com.lamvanhai.model.request.user.UserUpdateRequest;
import com.lamvanhai.model.response.user.UserResponse;
import com.lamvanhai.util.PasswordHasher;
import com.lamvanhai.util.TimeUtil;

import java.lang.reflect.InvocationTargetException;

import static com.lamvanhai.util.ReflectionUtil.mapper;

public class UserMapper {


    public static UserResponse mapToResponse(User user) {
        UserResponse userResponse = new UserResponse();
        try {
            mapper(user, userResponse);
            userResponse.setBirthday(TimeUtil.convertToString(user.getBirthday()));
            userResponse.setCreatedDate(TimeUtil.convertToString(user.getCreatedDate()));
            return userResponse;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();

            return null;
        }
    }

    public static User mapToEntity(UserSaveRequest userSaveRequest) {
        User user = new User();
        try {
            mapper(userSaveRequest, user);
            String password = PasswordHasher.hash(userSaveRequest.getPassword());
            user.setPassword(password);
            user.setBirthday(TimeUtil.convertToLocalDate(userSaveRequest.getBirthday()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public static User mapToEntity(UserUpdateRequest userUpdateRequest) {
        User user = new User();
        try {
            mapper(userUpdateRequest, user);
            user.setBirthday(TimeUtil.convertToLocalDate(userUpdateRequest.getBirthday()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}
