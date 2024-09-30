package com.example.securityapplication.Controller;

import com.example.securityapplication.Dto.LoginDto;
import com.example.securityapplication.Dto.SignUpDto;
import com.example.securityapplication.Dto.UserDto;
import com.example.securityapplication.Service.AuthService;
import com.example.securityapplication.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpDto signUpDto){
        UserDto userDto = userService.signUp(signUpDto);
        return ResponseEntity.ok(userDto);

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletResponse response){
        String token = authService.login(loginDto);

        Cookie cookie = new Cookie("token",token);
        cookie.setHttpOnly(true); // only we can see not other like attackers
        response.addCookie(cookie);

        return ResponseEntity.ok(token);


    }
}
