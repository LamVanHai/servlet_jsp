package com.lamvanhai.validate;

import com.lamvanhai.exception.ValidateException;
import com.lamvanhai.model.request.user.AuthRequest;
import org.apache.commons.lang3.StringUtils;

public class AuthValidator {


    public static AuthRequest userSaveValidate(AuthRequest authRequest) {
        return Validator
                .of(authRequest)
                .validate(authRequest1 -> authRequest1.getUserName(), AuthValidator::validateUserName, () -> new ValidateException("UserName is not blank"))
                .validate(authRequest1 -> authRequest1.getPassword(), AuthValidator::validatePassword, () -> new ValidateException("Password is not blank"))
                .get();
    }


    private static boolean validateUserName(String userName) {
        return StringUtils.isBlank(userName) ? true : false;
    }

    private static boolean validatePassword(String password) {
        return StringUtils.isBlank(password) ? true : false;
    }


}
