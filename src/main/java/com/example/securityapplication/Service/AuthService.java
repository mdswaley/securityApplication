package com.example.securityapplication.Service;

import com.example.securityapplication.Dto.LoginDto;
import com.example.securityapplication.Dto.LoginResponseDto;
import com.example.securityapplication.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;
    private final SessionService sessionService;


    public LoginResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
        );

        User user = (User) authentication.getPrincipal(); //In Spring Security, the Principal represents
        // the currently authenticated user or entity. It contains the details of the user who has successfully authenticated.

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        sessionService.generateNewSession(user,refreshToken);

        return new LoginResponseDto(user.getId(),accessToken,refreshToken);
    }

    public LoginResponseDto refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);

        sessionService.validateSession(refreshToken);

        User user = userService.getUserById(userId);

        String accessToken = jwtService.generateAccessToken(user);

        return new LoginResponseDto(user.getId(),accessToken,refreshToken);
    }
}

/*User sends email and password via a POST request (login form).
The AuthenticationManager invokes the authenticate() method using the provided credentials.
The UserDetailsService (UserService) checks if the email exists in the database.
The password provided by the user is verified against the stored (hashed) password in the database.
If authentication is successful, Spring Security returns an authenticated User object as the Principal.
You generate a JWT token for the authenticated user and return it.
*/
