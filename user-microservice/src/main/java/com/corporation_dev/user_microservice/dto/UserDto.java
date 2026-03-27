package com.corporation_dev.user_microservice.dto;

import lombok.Data;
import lombok.ToString;
import java.time.LocalDate;

@Data
@ToString
public class UserDto {
    private int id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String status;
    private LocalDate created_at;
    private LocalDate updated_at;
}
