package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int page, int size, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto editPost(long id, PostDto postDto);

    void deletePost(long id);

}
