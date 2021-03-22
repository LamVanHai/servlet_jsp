package com.lamvanhai.controller;

import com.lamvanhai.beans.BeanFactory;
import com.lamvanhai.model.response.role.RoleResponse;
import com.lamvanhai.model.response.user.UserResponse;
import com.lamvanhai.service.RoleService;
import com.lamvanhai.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/user")
public class UserController extends HttpServlet {

    private final RoleService roleService;
    private final UserService userService;

    public UserController() {
        this.roleService = (RoleService) BeanFactory.beans.get("roleService");
        this.userService = (UserService) BeanFactory.beans.get("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rs = req.getRequestDispatcher("/views/user/insertUser.jsp");
        List<RoleResponse> roles = roleService.findAll();
        req.setAttribute("roles", roles);
        String action = req.getParameter("action");
        if (action.equals("update")) {
            long id = Long.parseLong(req.getParameter("id"));
            UserResponse userResponse = userService.findById(id);
            List<RoleResponse> roleResponses = roleService.findAllRoleByUserId(id);
            userResponse.setRole(roleResponses.get(0));
            req.setAttribute("user", userResponse);
        }
        rs.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
