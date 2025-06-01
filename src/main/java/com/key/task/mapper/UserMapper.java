package com.key.task.mapper;


import com.key.task.entity.User;
import com.key.task.model.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}