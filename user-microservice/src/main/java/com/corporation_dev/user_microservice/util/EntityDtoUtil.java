package com.corporation_dev.user_microservice.util;

import org.springframework.beans.BeanUtils;

import com.corporation_dev.user_microservice.dto.UserDto;
import com.corporation_dev.user_microservice.entity.User;

public class EntityDtoUtil {
    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    public static User toEntity(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return user;
    }
}
