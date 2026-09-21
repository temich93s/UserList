package com.userlist.service;

import com.userlist.dto.UserDto;
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
    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(User::toUserDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(id)))
                .toUserDto();
    }

    @Transactional
    @Override
    public void addUser(UserDto userDto) {
        userRepository.save(userDto.toUser());
    }

    @Transactional
    @Override
    public void removeUserById(long id) {
        getUserById(id);
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateUser(UserDto userDto) {
        getUserById(userDto.getId());
        userRepository.save(userDto.toUser());
    }
}
