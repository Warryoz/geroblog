package com.springboot.blog.posts;

import com.springboot.blog.posts.PostDto;
import com.springboot.blog.posts.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int page, int size, String sortBy, String sortDirection);

    PostDto getPostById(long id);

    PostResponse getPostsByCategory(long categoryId, int page, int size, String sortBy, String sortDirection);

    PostDto updatePost(long id, PostDto postDto);

    void deletePost(long id);

}
