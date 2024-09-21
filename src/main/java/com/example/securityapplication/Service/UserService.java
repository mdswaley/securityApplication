package com.example.securityapplication.Service;

import com.example.securityapplication.Exception.ResourceNotFoundException;
import com.example.securityapplication.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username)
                .orElseThrow(()->new ResourceNotFoundException("User with email "+username+" is not present."));
    }
}
