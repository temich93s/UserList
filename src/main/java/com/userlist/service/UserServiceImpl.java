package com.userlist.service;

import com.userlist.exception.UserNotFoundException;
import com.userlist.model.User;
import com.userlist.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(id)));
    }

    @Transactional
    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void removeUserById(long id) {
        getUserById(id);
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        getUserById(user.getId());
        userRepository.save(user);
    }
}
