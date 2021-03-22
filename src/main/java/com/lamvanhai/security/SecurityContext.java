package com.lamvanhai.security;

import com.lamvanhai.model.request.user.AuthRequest;

import javax.servlet.http.HttpServletRequest;

public interface SecurityContext {

    String authorization(AuthRequest authRequest, HttpServletRequest request);

}
