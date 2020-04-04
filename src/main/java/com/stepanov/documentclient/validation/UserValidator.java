package com.stepanov.documentclient.validation;

import com.stepanov.documentclient.model.User;
import com.stepanov.documentclient.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private UserServiceImpl userService;

    @Autowired
    public UserValidator(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if (passwordMismatch(user)) {
            errors.rejectValue("verifyPassword", "", "пароли не совпадают");
        }
        if (loginRepeat(user)) {
            errors.rejectValue("login", "", "пользователь с таким логином уже существует");
        }
    }

    public boolean loginRepeat(User user) {
        String userLogin = user.getLogin();
        if (!(userLogin.isEmpty())) {
            User foundUserByLogin = userService.findByLogin(userLogin);
            return foundUserByLogin != null && userLogin.equals(foundUserByLogin.getLogin());
        }
        return false;
    }

    public boolean passwordMismatch(User user) {
        return !(user.getPassword().equals(user.getVerifyPassword()));
    }
}


