package com.lamvanhai.controller;

import com.lamvanhai.annotation.Autowire;
import com.lamvanhai.annotation.Controller;
import com.lamvanhai.beans.BeanFactory;
import com.lamvanhai.model.request.user.AuthRequest;
import com.lamvanhai.security.SecurityContext;
import com.lamvanhai.util.BeanUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
@Controller
public class LoginController extends HttpServlet {

    @Autowire
    private final SecurityContext securityContext;

    public LoginController() {
        securityContext = (SecurityContext) BeanFactory.beans.get("securityContext");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rs = req.getRequestDispatcher("/views/authen/login.jsp");
        rs.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthRequest authRequest = BeanUtil.toModel(AuthRequest.class, req);
        String url = securityContext.authorization(authRequest, req);
        resp.sendRedirect(url);
    }
}
