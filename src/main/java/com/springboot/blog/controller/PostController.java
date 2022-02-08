package com.springboot.blog.controller;

import com.springboot.blog.utils.AppConstants;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Api( value = "CRUD REST APIs for post resource")
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {


    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @ApiOperation( value = "Create new post REST API")
    @PreAuthorize("hasRole('ADMIN')")
    //Create blog rest api
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(service.createPost(postDto), HttpStatus.CREATED);
    }

    //Get all posts rest api
    @ApiOperation( value = "Get all post REST API")
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int size,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return service.getAllPosts(page, size, sortBy, sortDir);
    }

    // Get post by id rest api
    @ApiOperation( value = "Get post by id REST API")
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){
        return new ResponseEntity<>(service.getPostById(id), HttpStatus.OK);
    }

    @ApiOperation( value = "Edit post REST API")
    @PreAuthorize("hasRole('ADMIN')")
    // Edit post rest api
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> editPost(@PathVariable(name = "id") long id ,@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(service.editPost(id, postDto), HttpStatus.OK);
    }

    @ApiOperation( value = "Delete post REST API")
    @PreAuthorize("hasRole('ADMIN')")
    //Delete post rest api
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        service.deletePost(id);
        String responseMessage = String.format("Post with id %s was successfully deleted", id);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
}
