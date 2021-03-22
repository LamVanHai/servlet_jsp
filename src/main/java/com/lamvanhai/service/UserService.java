package com.lamvanhai.service;

import com.lamvanhai.model.request.user.AuthRequest;
import com.lamvanhai.model.request.user.UserSaveRequest;
import com.lamvanhai.model.request.user.UserUpdateRequest;
import com.lamvanhai.model.response.BaseResponse;
import com.lamvanhai.model.response.user.UserResponse;
import com.lamvanhai.paging.PageAble;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;

public interface UserService {
    UserResponse auth(AuthRequest authRequest) throws NoSuchAlgorithmException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;

    BaseResponse<UserResponse> getAll(PageAble pageAble);

    void save(UserSaveRequest userSaveRequest);

    void update(long id, UserUpdateRequest userUpdateRequest) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;

    UserResponse findById(long id);
}
