package com.corporation_dev.user_microservice.service;
import org.springframework.stereotype.Service;
import com.corporation_dev.user_microservice.dto.UserDto;
import com.corporation_dev.user_microservice.repository.UserRepository;
import com.corporation_dev.user_microservice.util.EntityDtoUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService { 

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Flux<UserDto> getAllUsers() {
        return this.userRepository.findAll()
            .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> insertUser(Mono<UserDto> userDto) {
        return userDto.map(EntityDtoUtil::toEntity)
            .flatMap(userRepository::save)
            .map(EntityDtoUtil::toDto);
    }

}
