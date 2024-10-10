package com.example.securityapplication.Controller;

import com.example.securityapplication.Service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logout")
@RequiredArgsConstructor
public class LogoutController {
    private final SessionService sessionService;

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> logout(@PathVariable Long userId){
        sessionService.deleteSession(userId);
        return ResponseEntity.ok("User logged out successfully.");
    }
}
