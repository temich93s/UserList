package com.userlist.service;

import com.userlist.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
    UserDto getUserById(long id);
    void addUser(UserDto userDto);
    void removeUserById(long id);
    void updateUser(UserDto userDto);
}
