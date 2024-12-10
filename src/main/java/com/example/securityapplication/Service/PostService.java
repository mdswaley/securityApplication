package com.example.securityapplication.Service;


import com.example.securityapplication.Dto.PostDto;
import com.example.securityapplication.Entity.PostEntity;
import com.example.securityapplication.Entity.User;
import com.example.securityapplication.Exception.ResourceNotFoundException;
import com.example.securityapplication.Repository.PostRepo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
public class PostService {
    private final PostRepo postRepo;
    private final ModelMapper modelMapper;

    public PostService(PostRepo postRepo, ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }

    public PostDto addData(PostDto postDto){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostEntity postEntity = modelMapper.map(postDto, PostEntity.class);
        postEntity.setAuthor(user);
        PostEntity saveEntity = postRepo.save(postEntity);
        return modelMapper.map(saveEntity,PostDto.class);
    }


    public PostDto updateData(PostDto input, Long id) {
        PostEntity olderData = postRepo.findById(id).orElse(null);
        input.setId(id);
        modelMapper.map(input,olderData);
        PostEntity saveData  = postRepo.save(olderData);

        return modelMapper.map(saveData,PostDto.class);
    }

    public PostDto getData(Long id) {
        isExistsByEmployeeId(id);
        PostEntity postEntity = postRepo.findById(id).orElse(null);
        return modelMapper.map(postEntity,PostDto.class);
    }

    public void isExistsByEmployeeId(Long employeeId) {
        boolean exists = postRepo.existsById(employeeId);
        if(!exists) throw new ResourceNotFoundException("Employee not found with id: "+employeeId);
    }

    public List<PostDto> getAllPosts() {
        List<PostEntity> postEntities = postRepo.findAll();
        return postEntities
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDto.class))
                .collect(Collectors.toList());
    }
}
