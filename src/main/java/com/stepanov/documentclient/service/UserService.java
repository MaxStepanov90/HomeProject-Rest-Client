package com.stepanov.documentclient.service;


import com.stepanov.documentclient.model.User;

public interface UserService {

    User findByLogin(String login);

    void saveUser(User user);

}
