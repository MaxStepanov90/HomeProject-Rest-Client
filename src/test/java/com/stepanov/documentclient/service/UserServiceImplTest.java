package com.stepanov.documentclient.service;

import com.stepanov.documentclient.model.User;
import com.stepanov.documentclient.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findByLogin_Should_Return_User() {
        User newUser = new User();
        newUser.setLogin("Max");
        when(userRepository.findByLogin("Max")).thenReturn(newUser);
        assertNotNull(userService.findByLogin("Max"));
        verify(userRepository, times(1)).findByLogin("Max");
    }

    @Test
    void findByLogin_TestCaptor() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        userService.findByLogin("Max");
        verify(userRepository).findByLogin(captor.capture());
        String argument = captor.getValue();
        assertEquals(argument, "Max");
    }
}