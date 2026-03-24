package com.corporation_dev.user_microservice.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.corporation_dev.user_microservice.dto.UserDto;
import com.corporation_dev.user_microservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("user")
@Tag(name = "User API", description = "Endpoints for managing users")
public class UserController {
    private UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all users", description = "Returns a list of all users in the system")
    @GetMapping("all")
    public Flux<UserDto> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @Operation(summary = "Insert a new user", description = "Inserts a new user into the system")
    @PostMapping("insert")
    public Mono<UserDto> insertUser(@RequestBody Mono<UserDto> userDto) {
        return this.userService.insertUser(userDto);
    } 

    @GetMapping("test")
    public Mono<String> testCircuit() {
        return userService.testCircuitBreaker();
    }
}
