package com.stepanov.documentclient.service;

import com.stepanov.documentclient.model.User;
import com.stepanov.documentclient.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(User.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(passwordEncoder.encode(user.getPassword()))
                .build());
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
