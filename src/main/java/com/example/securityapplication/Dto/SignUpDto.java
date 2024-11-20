package com.example.securityapplication.Dto;

import com.example.securityapplication.Entity.Enums.Roles;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {
    private String email;
    private String password;
    private String name;
    private Set<Roles> roles;
}
