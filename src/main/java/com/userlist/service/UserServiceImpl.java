package com.userlist.service;

import com.userlist.dao.UserDao;
import com.userlist.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    public boolean addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public boolean removeUserById(long id) {
        return userDao.removeUserById(id);
    }

    @Override
    public boolean updateUser(User updatedUser) {
        return userDao.updateUser(updatedUser);
    }
}
