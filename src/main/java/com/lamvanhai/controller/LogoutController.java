package com.lamvanhai.controller;

import com.lamvanhai.common.ConstantKey;
import com.lamvanhai.util.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionUtil.remove(req, ConstantKey.SESSION_KEY);

        resp.sendRedirect("/login");
    }
}