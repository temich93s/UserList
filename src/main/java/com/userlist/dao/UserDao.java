package com.userlist.dao;

import com.userlist.exception.UserNotFoundException;
import com.userlist.model.User;

import java.util.List;

public interface UserDao {
    List<User> getUsers();
    void addUser(User user);
    void removeUserById(long id);
    void updateUser(User user) throws UserNotFoundException;
}
