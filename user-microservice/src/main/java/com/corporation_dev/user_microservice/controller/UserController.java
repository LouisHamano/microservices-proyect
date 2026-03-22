package com.corporation_dev.user_microservice.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.corporation_dev.user_microservice.dto.UserDto;
import com.corporation_dev.user_microservice.service.UserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("all")
    public Flux<UserDto> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @PostMapping("insert")
    public Mono<UserDto> insertUser(@RequestBody Mono<UserDto> userDto) {
        return this.userService.insertUser(userDto);
    }
}
