package com.example.securityapplication.Dto;

import com.example.securityapplication.Entity.Enums.Permission;
import com.example.securityapplication.Entity.Enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {
    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
    private Set<Permission> permissions;
}
