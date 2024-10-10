package com.example.securityapplication.Config;

import com.example.securityapplication.Filters.JwtAuthFilter;
import com.example.securityapplication.Filters.LoggingFilter;
import com.example.securityapplication.handler.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class webSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
//        httpSecurity
//                .formLogin(Customizer.withDefaults());//default form means only form not give authorize or authentication in there.

//        but with service bean we can't do this bcz of repository it check is user details present in the database or not
        httpSecurity
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/post","/auth/**","/home.html","/logout/**").permitAll() //now this request path is public for all (/post/**) this is for all request get public
                        //.requestMatchers("/post/**").hasAnyRole("ADMIN") // only admin can log in inside /post/**
                        .anyRequest().authenticated()) // now we add authorize for any request and we can authenticate it.

                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionConfig->sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //NEVER:- create session never use
                                                                           // ALWAYS:- can create and also use
                                                                            //IF_REQUIRED:-not create but use if it is present
                                                                            //STATELESS:- neither create nor use
//                if you use order annotation then no need to handle filter there
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//                .oauth2Login(oauthConf->oauthConf
//                        .failureUrl("/login?error=true")
//                        .successHandler(oAuth2SuccessHandler)
//                );



            //    .formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }

//    @Bean
//    UserDetailsService getUserDetails(){
//        UserDetails normalUser = User
//                .withUsername("MD")
//                .password(passwordEncoder().encode("mds"))
//                .roles("USER")
//                .build();
//        UserDetails adminUser = User
//                .withUsername("Swaley")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(normalUser, adminUser);
//    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration conf) throws Exception{
        return conf.getAuthenticationManager();
    }
}
