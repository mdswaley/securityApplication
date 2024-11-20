package com.example.securityapplication.Dto;

import com.example.securityapplication.Entity.Enums.Roles;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;

}
