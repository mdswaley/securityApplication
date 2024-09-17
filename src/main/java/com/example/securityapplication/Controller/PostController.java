package com.example.securityapplication.Controller;


import com.example.securityapplication.Dto.PostDto;
import com.example.securityapplication.Service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public PostDto addData(@RequestBody PostDto postDto){
        return postService.addData(postDto);
    }

    @GetMapping(path = "/{id}")
    public PostDto getData(@PathVariable Long id){
        return postService.getData(id);
    }

    @PutMapping("/{id}")
    public PostDto updateData(@RequestBody PostDto postDto,@PathVariable Long id){
        return postService.updateData(postDto,id);
    }

    @GetMapping
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }
}
