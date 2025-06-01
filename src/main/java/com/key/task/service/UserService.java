package com.key.task.service;

import com.key.task.model.UserDto;

import java.util.Optional;

public interface UserService {
    Optional<UserDto> getUserByEmail(String email);
    void createUser(UserDto userDto);
}
