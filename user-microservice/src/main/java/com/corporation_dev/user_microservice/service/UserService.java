package com.corporation_dev.user_microservice.service;
import org.springframework.stereotype.Service;
import com.corporation_dev.user_microservice.dto.UserDto;
import com.corporation_dev.user_microservice.repository.UserRepository;
import com.corporation_dev.user_microservice.util.EntityDtoUtil;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService { 
    private UserRepository userRepository;    
    private WebClient webClient;

    public UserService(UserRepository userRepository, WebClient webClient) {
        this.userRepository = userRepository;
        this.webClient = webClient;
    }

    public Flux<UserDto> getAllUsers() {
        return this.userRepository.findAll()
            .map(EntityDtoUtil::toDto);
    }

    @CircuitBreaker(name = "insertUserCircuitBreaker", fallbackMethod = "insertUserFallback")
    public Mono<UserDto> insertUser(Mono<UserDto> userDto) {
        return userDto.map(EntityDtoUtil::toEntity)
            .flatMap(userRepository::save)
            .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> insertUserFallback(Mono<UserDto> userDto, Throwable ex) {
        System.out.println("Fallback insertUser - Error: " + ex.getMessage());
        return Mono.error(new RuntimeException("Servicio de usuarios no disponible temporalmente"));
    }

    @CircuitBreaker(name = "testCircuitBreaker", fallbackMethod = "testCircuitBreakerFallback")
    public Mono<String> testCircuitBreaker() {
        return webClient
            .get()
            .uri("http://www.googleerror.com")
            .retrieve()
            .bodyToMono(String.class)
            .map(res -> "Operación exitosa")
            .doOnError(ex -> System.out.println("-> Error capturado: " + ex.getClass().getSimpleName()))
            .onErrorMap(ex -> new RuntimeException("Servicio de prueba no disponible"));
    }

    public Mono<String> testCircuitBreakerFallback(Throwable ex) {
        System.out.println("-> FALLBACK ejecutado: Servicio temporalmente no disponible");
        return Mono.just("Fallback: Servicio no disponible");
    }

    /* Falta implementar */
    // Actualizar un usuario existente
    // public Mono<User> updateUser(int id, User userDetails) {
    //     return userRepository.findById(id)
    //         .flatMap(existingUser -> {
    //             existingUser.setName(userDetails.getName());
    //             existingUser.setEmail(userDetails.getEmail());
    //             existingUser.setPassword(userDetails.getPassword());
    //             existingUser.setRole(userDetails.getRole());
    //             existingUser.setStatus(userDetails.getStatus());
    //             return userRepository.save(existingUser);
    //         });
    // }

    // // Eliminar un usuario
    // public Mono<Void> deleteUser(int id) {
    //     return userRepository.deleteById(id);
    // }
}
