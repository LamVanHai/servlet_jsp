package com.lamvanhai.security;

import com.lamvanhai.common.ConstantKey;
import com.lamvanhai.util.SessionUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class Authentication implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String url = req.getRequestURI();

        if (url.contains("/login") || url.equals("/") || url.startsWith("/assets") || url.contains("api")) {
            chain.doFilter(req, response);
            return;
        }

        Object key = SessionUtil.get(req, ConstantKey.SESSION_KEY);
        if (key == null) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/views/authen/login.jsp");
            req.setAttribute("message", "Vui lòng đăng nhập để truy cập");
            requestDispatcher.forward(req, res);
        }

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
