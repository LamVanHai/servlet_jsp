package com.lamvanhai.controller.api;

import com.lamvanhai.beans.BeanFactory;
import com.lamvanhai.model.request.user.UserSaveRequest;
import com.lamvanhai.model.request.user.UserUpdateRequest;
import com.lamvanhai.service.UserService;
import com.lamvanhai.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/api/user")
public class UserApi extends HttpServlet {

    private final UserService userService;

    public UserApi() {
        this.userService = (UserService) BeanFactory.beans.get("userService");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserSaveRequest userSaveRequest = JsonUtil.toClass(req, UserSaveRequest.class);
        userService.save(userSaveRequest);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserUpdateRequest userUpdateRequest = JsonUtil.toClass(req, UserUpdateRequest.class);
            userService.update(userUpdateRequest.getId(), userUpdateRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
