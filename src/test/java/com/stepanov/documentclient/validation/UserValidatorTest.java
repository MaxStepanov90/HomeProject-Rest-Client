package com.stepanov.documentclient.validation;

import com.stepanov.documentclient.model.User;
import com.stepanov.documentclient.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
class UserValidatorTest {

    @InjectMocks
    private UserValidator userValidator;
    @Mock
    private UserServiceImpl userService;
    @Mock
    Errors errors;

    private User newUserMax() {
        return User.builder()
                .login("Max")
                .password("11111")
                .build();
    }

    @Test
    void validate_Should_Not_Accept_User_With_Empty_Credentials() {
        User user = new User();
        user.setPassword("");
        user.setVerifyPassword("");
        user.setLogin("");

        userValidator.validate(user, errors);
        verify(errors, never()).rejectValue(eq("verifyPassword"), any(), any());
        verify(errors, never()).rejectValue(eq("login"), any(), any());
    }

    @Test
    void validate_Should_Not_Accept_User_With_Bad_Credentials() {
        User user = new User();
        user.setPassword("");
        user.setVerifyPassword("11111");
        user.setLogin("Max");

        when(userService.findByLogin("Max")).thenReturn(newUserMax());
        userValidator.validate(user, errors);
        verify(errors, times(1)).rejectValue(eq("verifyPassword"), any(), any());
        verify(errors, times(1)).rejectValue(eq("login"), any(), any());
    }

    @Test
    void loginRepeat_Should_Return_True() {
        User newUser = new User();
        newUser.setLogin("Max");
        when(userService.findByLogin("Max")).thenReturn(newUserMax());
        assertTrue(userValidator.loginRepeat(newUser));
    }

    @Test
    void loginRepeat_Should_Return_False() {
        User newUser = new User();
        newUser.setLogin("Alex");
        when(userService.findByLogin("Max")).thenReturn(newUserMax());
        assertFalse(userValidator.loginRepeat(newUser));
    }

    @Test
    void loginRepeat_With_Empty_Login_Should_Return_False() {
        User newUser = new User();
        newUser.setLogin("");
        when(userService.findByLogin("Max")).thenReturn(newUserMax());
        assertFalse(userValidator.loginRepeat(newUser));
    }

    @Test
    void passwordMismatch_Should_Return_False() {
        User user = new User();
        user.setPassword("11111");
        user.setVerifyPassword("11111");
        assertFalse(userValidator.passwordMismatch(user));
    }

    @Test
    void passwordMismatch_Should_Return_True() {
        User user = new User();
        user.setPassword("11111");
        user.setVerifyPassword("22222");
        assertTrue(userValidator.passwordMismatch(user));
    }
}