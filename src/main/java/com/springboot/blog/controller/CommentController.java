package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Api(value = "CRUD comments REST APIs")
@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @ApiOperation( value = "Create new comment REST API")
    @PostMapping()
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
                                                    @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(service.createComment(postId, commentDto), HttpStatus.OK);
    }

    @ApiOperation( value = "Get comments by post REST API")
    @GetMapping()
    public List<CommentDto> getCommentsByPostId(@PathVariable( value = "postId") Long postId){
        return  service.getCommentsByPostId(postId);
    }


    @ApiOperation( value = "Get comment by id REST API")
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable( value = "postId") Long postId,
                                                     @PathVariable( value = "commentId") Long commentId){
        return new ResponseEntity<>(service.getCommentById(postId, commentId), HttpStatus.OK);
    }

    @ApiOperation( value = "Update comment REST API")
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto>  updateComment(@PathVariable( value = "postId") Long postId,
                                                     @PathVariable( value = "commentId") Long commentId,
                                                     @Valid @RequestBody CommentDto commentDto) {

        return new ResponseEntity<>(service.updateComment(postId, commentId, commentDto), HttpStatus.OK);
    }

    @ApiOperation( value = "Delete comment REST API")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable( value = "postId") Long postId,
                                                @PathVariable( value = "commentId") Long commentId){
        service.deleteComment(postId, commentId);
        return  new ResponseEntity<>("Comment successfully deleted", HttpStatus.OK);
    }
}
