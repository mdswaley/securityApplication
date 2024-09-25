package com.example.securityapplication.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class webSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
//        httpSecurity
//                .formLogin(Customizer.withDefaults());//default form means only form not give authorize or authentication in there.

//        but with service bean we can't do this bcz of repository it check is user details present in the database or not
        httpSecurity
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/post").permitAll() //now this request path is public for all (/post/**) this is for all request get public
                        .requestMatchers("/post/**").hasAnyRole("ADMIN") // only admin can log in inside /post/**
                        .anyRequest().authenticated()) // now we add authorize for any request and we can authenticate it.

//                .csrf(csrfConfig->csrfConfig.disable())
//                .sessionManagement(sessionConfig->sessionConfig
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //NEVER:- create session never use
                                                                            //ALWAYS:- can create and also use
                                                                            //IF_REQUIRED:-not create but use if it is present
                                                                            //STATELESS:- neither create nor use
                .formLogin(Customizer.withDefaults());
        ;
        return httpSecurity.build();
    }

    @Bean
    UserDetailsService getUserDetails(){
        UserDetails normalUser = User
                .withUsername("MD")
                .password(passwordEncoder().encode("mds"))
                .roles("USER")
                .build();
        UserDetails adminUser = User
                .withUsername("Swaley")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(normalUser, adminUser);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
