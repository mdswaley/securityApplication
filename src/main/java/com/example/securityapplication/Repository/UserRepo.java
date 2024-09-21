package com.example.securityapplication.Repository;

import com.example.securityapplication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
//    we are find by email here bcz our authentication userName finding through email
    Optional<User> findByEmail(String email);
}
