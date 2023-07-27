package com.springboot.blog.posts;

import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
@Tag(name = "CRUD REST APIs for post resource")

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(
            summary = "Get all posts REST API",
            description = "This API is used to get all posts"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Posts retrieved successfully"
    )
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int size,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection
    ) {
        return postService.getAllPosts(page, size, sortBy, sortDirection);
    }

    @Operation(
            summary = "Get all posts by category REST API",
            description = "This API is used to get all posts by category"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Posts by category retrieved successfully"
    )
    @GetMapping("/category/{categoryId}")
    public PostResponse getPostsByCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int size,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection
    ) {
        return postService.getPostsByCategory(categoryId, page, size, sortBy, sortDirection);
    }

    @Operation(
            summary =  "Create new post REST API",
            description = "This API is used to create new post"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Post created successfully"
    )
    @SecurityRequirement(
            name="Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @Operation(
            summary =  "Get post by id REST API",
            description = "This API is used to get post by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Post retrieved successfully"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @Operation(
            summary =  "Edit post REST API",
            description = "This API is used to edit post"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Post edited successfully"
    )
    @SecurityRequirement(
            name="Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    // Edit post rest api
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable(name = "id") long id ,@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.updatePost(id, postDto), HttpStatus.OK);
    }

    @Operation(
            summary =  "Delete post REST API",
            description = "This API is used to delete post"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Post deleted successfully"
    )
    @SecurityRequirement(
            name="Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    //Delete post rest api
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        postService.deletePost(id);
        var responseMessage = String.format("Post with id %s was successfully deleted", id);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
}
