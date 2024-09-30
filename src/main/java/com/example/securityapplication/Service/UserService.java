package com.example.securityapplication.Service;

import com.example.securityapplication.Dto.LoginDto;
import com.example.securityapplication.Dto.SignUpDto;
import com.example.securityapplication.Dto.UserDto;
import com.example.securityapplication.Entity.User;
import com.example.securityapplication.Exception.ResourceNotFoundException;
import com.example.securityapplication.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;
//    private final JWTService jwtService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username)
                .orElseThrow(()->new ResourceNotFoundException("User with email "+username+" is not present."));
    }

    public UserDto signUp(SignUpDto signUpDto) {
        Optional<User> user = userRepo.findByEmail(signUpDto.getEmail());
        if (user.isPresent()){
            throw new BadCredentialsException("User with email "+signUpDto.getEmail()+" already exists");
        }

        User createUser = modelMapper.map(signUpDto,User.class);
        createUser.setPassword(passwordEncoder.encode(createUser.getPassword()));

        User save = userRepo.save(createUser);
        return modelMapper.map(save,UserDto.class);
    }


//    when i got Circular Dependency
    /*If you have something like this:
       AuthController depends on UserService.
        UserService depends on AuthenticationManager.
        AuthenticationManager might depend on UserService (either directly or indirectly,
        for example, through UserDetailsService).*/


}
