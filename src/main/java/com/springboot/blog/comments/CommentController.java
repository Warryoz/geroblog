package com.springboot.blog.comments;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;


@Tag(name = "CRUD comments REST APIs")

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(
            summary = "Create new comment REST API",
            description = "Create new comment with postId"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Comments successfully retrieved"
    )
    @PostMapping()
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
                                                    @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Get comments by post REST API",
            description = "Get comments by postId"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of comments successfully retrieved"
    )
    @GetMapping()
    public List<CommentDto> getCommentsByPostId(@PathVariable( value = "postId") Long postId){
        return  commentService.getCommentsByPostId(postId);
    }


    @Operation(
            summary = "Get comment by id REST API",
            description = "Get comment by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Comment successfully retrieved"
    )
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable( value = "postId") Long postId,
                                                     @PathVariable( value = "commentId") Long commentId){
        return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }

    @Operation(
            summary = "Update comment REST API",
            description = "Update comment with postId"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Comment successfully updated"
    )
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto>  updateComment(@PathVariable( value = "postId") Long postId,
                                                     @PathVariable( value = "commentId") Long commentId,
                                                     @Valid @RequestBody CommentDto commentDto) {

        return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete comment REST API",
            description = "Delete comment with postId"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Comment successfully deleted"
    )
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable( value = "postId") Long postId,
                                                @PathVariable( value = "commentId") Long commentId){
        commentService.deleteComment(postId, commentId);
        return  new ResponseEntity<>("Comment successfully deleted", HttpStatus.OK);
    }
}
