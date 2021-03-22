package com.lamvanhai.security;

import com.lamvanhai.annotation.Autowire;
import com.lamvanhai.annotation.Component;
import com.lamvanhai.common.ConstantKey;
import com.lamvanhai.model.request.user.AuthRequest;
import com.lamvanhai.model.response.user.UserResponse;
import com.lamvanhai.service.RoleService;
import com.lamvanhai.service.UserService;
import com.lamvanhai.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecurityContextImpl implements SecurityContext {

    @Autowire
    private final UserService userService;
    @Autowire
    private final RoleService roleService;

    public SecurityContextImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public String authorization(AuthRequest authRequest, HttpServletRequest request) {

        try {
            UserResponse user = userService.auth(authRequest);
            if (user != null ) {
                SessionUtil.put(request, ConstantKey.SESSION_KEY, user.getUserName());
                List<String> roles = roleService
                        .findAllRoleByUserId(user.getId())
                        .stream()
                        .map(role -> role.getName())
                        .collect(Collectors.toList());

                if (roles.contains("admin")) {
                    return "/admin?pageIndex=1&pageSize=5";
                } else {
                    return "/home";
                }
            }

            return "/login?message=tai khoan hoac mat khau khong ton tai";
        } catch (NoSuchAlgorithmException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();

            return "";
        }

    }
}
