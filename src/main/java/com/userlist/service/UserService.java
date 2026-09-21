package com.userlist.service;

import com.userlist.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User getUserById(long id);
    void addUser(User user);
    void removeUserById(long id);
    void updateUser(User user);
}
