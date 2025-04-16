package com.example.securityapplication.Controller;


import com.example.securityapplication.Dto.PostDto;
import com.example.securityapplication.Service.PostService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/post")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public PostDto addData(@RequestBody PostDto postDto){
        return postService.addData(postDto);
    }

//    all are use for role and permission based security (until ##)

//    @GetMapping(path = "/{id}")
//    @PreAuthorize("hasRole('ADMIN') AND hasAuthority('POST_VIEW')") // we can use OR also instead of AND (same in programming meaning)
//    public PostDto getData(@PathVariable Long id){
//        return postService.getData(id);
//    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("@postSecurity.isOwner(#id)") // we use method level security
    public PostDto getData(@PathVariable Long id){
        return postService.getData(id);
    }

    @GetMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN"}) // role based authentication using array of roles.
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }

//    ##

    @PutMapping("/{id}")
    public PostDto updateData(@RequestBody PostDto postDto,@PathVariable Long id){
        return postService.updateData(postDto,id);
    }

}
