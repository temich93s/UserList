package com.userlist.service;

import com.userlist.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    boolean addUser(User user);
    boolean removeUserById(long id);
    boolean updateUser(User updatedUser);
}
