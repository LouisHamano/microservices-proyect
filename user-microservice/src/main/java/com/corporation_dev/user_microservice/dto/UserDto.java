package com.corporation_dev.user_microservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDto {
    private int id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String status;
    private String created_at;
    private String updated_at;
}
