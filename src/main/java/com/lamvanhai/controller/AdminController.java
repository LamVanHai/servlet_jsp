package com.lamvanhai.controller;

import com.lamvanhai.annotation.Autowire;
import com.lamvanhai.beans.BeanFactory;
import com.lamvanhai.model.response.BaseResponse;
import com.lamvanhai.model.response.user.UserResponse;
import com.lamvanhai.paging.PageRequest;
import com.lamvanhai.service.UserService;
import com.lamvanhai.util.BeanUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin")
public class AdminController extends HttpServlet {

    @Autowire
    private UserService userService;


    public AdminController() {
        this.userService = (UserService) BeanFactory.beans.get("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PageRequest pageRequest = BeanUtil.toModel(PageRequest.class, req);
        BaseResponse<UserResponse> users = userService.getAll(pageRequest);
        long totalPage = (users.getTotalItem() / pageRequest.getSize()) + 1;
        long currentPage = pageRequest.getPageIndex();
        users.setCurrentPage(currentPage);
        users.setTotalPage(totalPage);
        req.setAttribute("users", users);

        RequestDispatcher rs = req.getRequestDispatcher("/views/admin.jsp");
        rs.forward(req, resp);
    }
}
