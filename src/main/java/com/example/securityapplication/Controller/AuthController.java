package com.example.securityapplication.Controller;

import com.example.securityapplication.Dto.LoginDto;
import com.example.securityapplication.Dto.LoginResponseDto;
import com.example.securityapplication.Dto.SignUpDto;
import com.example.securityapplication.Dto.UserDto;
import com.example.securityapplication.Service.AuthService;
import com.example.securityapplication.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @Value("${deploy.env}")
    private String devEnv;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpDto signUpDto){
        UserDto userDto = userService.signUp(signUpDto);
        return ResponseEntity.ok(userDto);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto, HttpServletResponse response){
        LoginResponseDto loginResponseDto = authService.login(loginDto);

        Cookie cookie = new Cookie("refreshToken",loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true); // only we can see not other like attackers
        cookie.setSecure("production".equals(devEnv));
        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request, HttpServletResponse response) {
        String oldRefreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh Token not Found Inside the Cookies."));


        LoginResponseDto loginResponseDto = authService.rotateTokens(oldRefreshToken);

        // Set the new refresh token as a cookie
        Cookie newRefreshTokenCookie = new Cookie("refreshToken", loginResponseDto.getRefreshToken());
        newRefreshTokenCookie.setHttpOnly(true);
        newRefreshTokenCookie.setSecure("production".equals(devEnv));
        newRefreshTokenCookie.setPath("/auth");
        response.addCookie(newRefreshTokenCookie);

        return ResponseEntity.ok(loginResponseDto);
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request,HttpServletResponse response){
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new IllegalStateException("Refresh token not found in cookies"));


        authService.deleteSessionByRefreshToken(refreshToken);


        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(0);
        cookie.setPath("/auth");
        response.addCookie(cookie);

        return ResponseEntity.ok("Logged out successfully.");
    }
}
