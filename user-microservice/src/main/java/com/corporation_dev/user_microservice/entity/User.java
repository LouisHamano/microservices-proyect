package com.corporation_dev.user_microservice.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;
import lombok.ToString;
import java.time.LocalDate;

@Data
@ToString
@Table("users")
public class User {
    @Id
    @Column("ID")
    private int id;
    @Column("NAME")
    private String name;
    @Column("EMAIL")
    private String email;
    @Column("PASSWORD")
    private String password;
    @Column("ROLE")
    private String role;
    @Column("STATUS")
    private String status;
    @Column("CREATED_AT")
    private LocalDate created_at;
    @Column("UPDATED_AT")
    private LocalDate updated_at;
}   
