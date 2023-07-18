package com.springboot.blog.service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int page, int size, String sortBy, String sortDirection);

    PostDto getPostById(long id);

    PostResponse getPostsByCategory(Long categoryId, int page, int size, String sortBy, String sortDirection);

    PostDto updatePost(long id, PostDto postDto);

    void deletePost(long id);

}
