package com.lamvanhai.service.impl;

import com.lamvanhai.annotation.Autowire;
import com.lamvanhai.annotation.Service;
import com.lamvanhai.entity.User;
import com.lamvanhai.exception.ObjectNotFoundException;
import com.lamvanhai.exception.ValidateException;
import com.lamvanhai.listerner.ApplicationListener;
import com.lamvanhai.mapper.UserMapper;
import com.lamvanhai.model.request.user.AuthRequest;
import com.lamvanhai.model.request.user.UserSaveRequest;
import com.lamvanhai.model.request.user.UserUpdateRequest;
import com.lamvanhai.model.response.BaseResponse;
import com.lamvanhai.model.response.user.UserResponse;
import com.lamvanhai.paging.PageAble;
import com.lamvanhai.repository.UserRepository;
import com.lamvanhai.service.UserService;
import com.lamvanhai.util.PasswordHasher;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowire
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
//        Stream<User> userStream = userRepository.findAll();
//        userStream.forEach(user -> {
//                ApplicationListener.userCache.put(user.getUserName(), UserMapper.mapToResponse(user));
//        });
    }

    // design pattern Singleton
    private volatile static UserServiceImpl userService;

    public static UserServiceImpl getInstance(UserRepository userRepository) {
        if (userService == null) {
            synchronized (UserServiceImpl.class) {
                if (userService == null) {
                    userService = new UserServiceImpl(userRepository);
                }
            }
        }
        return userService;
    }

    @Override
    public UserResponse auth(AuthRequest authRequest) throws NoSuchAlgorithmException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (StringUtils.isBlank(authRequest.getUserName())) {
            throw new ValidateException("UserName is not blank");
        }
        if (StringUtils.isBlank(authRequest.getPassword())) {
            throw new ValidateException("Password is not blank");
        }

        UserResponse userResponse = ApplicationListener.userCache.get(authRequest.getUserName());

        String password = PasswordHasher.hash(authRequest.getPassword());
        if (userResponse == null) {

            Optional<User> user = userRepository.findUserByUserNameAndPassword(authRequest.getUserName(), password);

            user.orElseThrow(() -> new ObjectNotFoundException("user not found"));

            userResponse = UserMapper.mapToResponse(user.get());

            ApplicationListener.userCache.put(userResponse.getUserName(), userResponse);

            return userResponse;
        }

        if (userResponse.getPassword().equals(password)) {
            return userResponse;
        }

        return null;
    }

    @Override
    public BaseResponse<UserResponse> getAll(PageAble pageAble) {
        List<User> users = userRepository.findAll(pageAble).collect(Collectors.toList());
        List<UserResponse> data = users.stream().map(UserMapper::mapToResponse).collect(Collectors.toList());
        long total = userRepository.count();
        return BaseResponse.of(total, data);
    }

    @Override
    public void save(UserSaveRequest userSaveRequest) {
        User user = UserMapper.mapToEntity(userSaveRequest);
        try {
            userRepository.insert(user);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(long id, UserUpdateRequest userUpdateRequest) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        User user = UserMapper.mapToEntity(userUpdateRequest);
        userRepository.update(id, user);
    }

    @Override
    public UserResponse findById(long id) {
        return UserMapper.mapToResponse(userRepository.findById(id).get());
    }
}
