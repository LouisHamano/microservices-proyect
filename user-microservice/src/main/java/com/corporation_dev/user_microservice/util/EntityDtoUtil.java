package com.corporation_dev.user_microservice.util;
import org.springframework.beans.BeanUtils;
import com.corporation_dev.user_microservice.dto.UserDto;
import com.corporation_dev.user_microservice.entity.User;
import java.time.LocalDate;

public class EntityDtoUtil {
    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    public static User toEntity(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        if (user.getCreated_at() == null) {
            user.setCreated_at(LocalDate.now());
        }
        if (user.getUpdated_at() == null) {
            user.setUpdated_at(LocalDate.now());
        }
        return user;
    }
}
