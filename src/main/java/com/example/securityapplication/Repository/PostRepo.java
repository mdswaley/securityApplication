package com.example.securityapplication.Repository;

import com.example.securityapplication.Entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<PostEntity,Long> {
}
