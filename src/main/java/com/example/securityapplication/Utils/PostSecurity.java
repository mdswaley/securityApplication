package com.example.securityapplication.Utils;

import com.example.securityapplication.Dto.PostDto;
import com.example.securityapplication.Entity.User;
import com.example.securityapplication.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurity {
    private final PostService postService;

    public boolean isOwner(Long postId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDto postDto = postService.getData(postId);
        return postDto.getAuthor().getId().equals(user.getId());
    }
}
