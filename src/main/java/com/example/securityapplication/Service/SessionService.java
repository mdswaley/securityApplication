package com.example.securityapplication.Service;

import com.example.securityapplication.Entity.Session;
import com.example.securityapplication.Entity.User;
import com.example.securityapplication.Repository.SessionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepo sessionRepo;
    private final int SESSION_LIMIT = 2;

    public void generateNewSession(User user,String refreshToken){
        List<Session> userSession = sessionRepo.findByUser(user); //find the current user

        if(userSession.size() == SESSION_LIMIT){
            userSession.sort(Comparator.comparing(Session::getLastUsedAt)); // sort according to the time
            // ex:- prev:-9:30, curr:- 10:20 then delete after sort 1st session

            Session leastRecentlyUsedSession = userSession.get(0);

            sessionRepo.delete(leastRecentlyUsedSession);
        }

        Session newSession = Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();

        sessionRepo.save(newSession);
    }

    public void validateSession(String refreshToken){
        Session session = sessionRepo.findByRefreshToken(refreshToken)
                .orElseThrow(()->new SessionAuthenticationException("Session not found for this refresh token: "+refreshToken));
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepo.save(session);
    }

    public void deleteSession(Long userId) {
        List<Session> session = sessionRepo.findByUserId(userId);

        if (session.isEmpty()) {
            throw new SessionAuthenticationException("No sessions found for user ID: " + userId);
        }
        sessionRepo.deleteAll(session);
    }

}
