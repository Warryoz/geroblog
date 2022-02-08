package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.config.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository repository;
    private final ModelMapper mapper;

    public PostServiceImpl(PostRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //convert Dto to entity
        Post post = mapToEntity(postDto);
        Post newPost = repository.save(post);
        // convert entity to Dto
        return mapToDto(newPost);
    }

    @Override
    public PostResponse getAllPosts(int page, int size, String sortBy, String sortDir) {

        //Create Sort instant
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        //Create pageable instant
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Post> posts = repository.findAll(pageable);

        //get content for page object
        List<Post> postList = posts.getContent();
        // Convert list entity to list dto
        List<PostDto> content = postList.stream().map(this::mapToDto).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse(
                content,
                posts.getNumber(),
                posts.getSize(),
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.isLast()
        );
        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto editPost(long id, PostDto postDto) {
        Post post = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        repository.save(post);
        return mapToDto(post);
    }

    @Override
    public void deletePost(long id){
        Post post = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        repository.deleteById(id);
    }

    // Convert entity to dto
    private PostDto mapToDto(Post post){
        PostDto postDto = mapper.map(post, PostDto.class);
        /*postDto.setId(post.getId());
        postDto.setContent(post.getContent());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription()); */
        return postDto;
    }

    //Convert dto to entity
    private Post mapToEntity(PostDto postDto){
        Post post = mapper.map(postDto, Post.class);
        /*post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent()); */
        return post;
    }
}
