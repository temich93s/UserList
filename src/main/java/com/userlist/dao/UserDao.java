package com.userlist.dao;

import com.userlist.model.User;

import java.util.List;

public interface UserDao {
    List<User> getUsers();
    boolean addUser(User user);
    boolean removeUserById(long id);
    boolean updateUser(User updatedUser);
}
