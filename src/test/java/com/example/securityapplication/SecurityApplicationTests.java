package com.example.securityapplication;

import com.example.securityapplication.Entity.User;
import com.example.securityapplication.Service.JWTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityApplicationTests {
    @Autowired
    private JWTService jwtService;
    @Test
    void contextLoads() {
        User user = new User(4L,"adb@gmail.com","123","MD");
        String token = jwtService.generateAccessToken(user);
        System.out.println(token);

        Long id = jwtService.getUserIdFromToken(token);
        System.out.println(id);
    }

}
