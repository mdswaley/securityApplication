package com.example.securityapplication.Repository;

import com.example.securityapplication.Entity.Session;
import com.example.securityapplication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepo extends JpaRepository<Session,Long> {
    List<Session> findByUser(User user);
    Optional<Session> findByRefreshToken(String refreshToken);
}
