package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.config.exception.BlogApiException;
import com.springboot.blog.config.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl  implements CommentService {

    private final CommentRepository repository;
    private final PostRepository postRepository;
    private final ModelMapper mapper;

    public CommentServiceImpl(CommentRepository repository, PostRepository postRepository, ModelMapper mapper) {
        this.repository = repository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        // map to Entity
        Comment comment = mapToEntity(commentDto);

        // retrieve  post  entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id",postId));

        // set post to comment entity
        comment.setPost(post);

        // save comment
        Comment newComment = repository.save(comment);

        // map to Dto
        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        return repository.findByPostId(postId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Comment comment = findAndValidateComment(postId,commentId);
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, long commentId, CommentDto commentDto) {
        Comment comment = findAndValidateComment(postId,commentId);
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = repository.save(comment);
        return  mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = findAndValidateComment(postId,commentId);
        repository.delete(comment);
    }

    private Comment findAndValidateComment(Long postId, Long commentId){
        // retrieve  post  entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id",postId));

        // retrieve comment by id
        Comment comment = repository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw  new BlogApiException(HttpStatus.BAD_REQUEST, "Comment doesn't belong to a post");
        }
        return comment;
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = mapper.map(comment, CommentDto.class);
       /* CommentDto commentDto = new CommentDto(
                comment.getId(),
                comment.getName(),
                comment.getEmail(),
                comment.getBody()
        );
        */
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = mapper.map(commentDto, Comment.class);
       /* comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());*/
        return comment;
    }




}
