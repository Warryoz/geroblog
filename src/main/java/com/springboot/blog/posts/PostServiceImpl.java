package com.springboot.blog.posts;

import com.springboot.blog.categories.Category;
import com.springboot.blog.categories.CategoryRepository;
import com.springboot.blog.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Override
    public PostDto createPost(PostDto postDto) {
        Category postCategory = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
        //convert Dto to entity
        Post post = mapToEntity(postDto);
        post.setCategory(postCategory);

        Post newPost = postRepository.save(post);
        // convert entity to Dto
        PostDto newPostDto = mapToDto(newPost);
        return newPostDto;
    }

    @Override
    public PostResponse getAllPosts(int page, int size, String sortBy, String sortDirection) {

        //Create Sort instant
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        //Create pageable instant
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Post> posts = postRepository.findAll(pageable);

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
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostResponse getPostsByCategory(long categoryId, int page, int size, String sortBy, String sortDirection) {
        Category postCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        //Create Sort instant
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        //Create pageable instant
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> posts = postRepository.findByCategoryId(postCategory.getId(), pageable);
        //get content for page object
        List<Post> postList = posts.getContent();

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
    public PostDto updatePost(long id, PostDto postDto) {

        Category postCategory = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(postCategory);
        postRepository.save(post);
        return mapToDto(post);
    }

    @Override
    public void deletePost(long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.deleteById(id);
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
