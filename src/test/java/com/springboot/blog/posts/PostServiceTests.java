package com.springboot.blog.posts;

import com.springboot.blog.categories.Category;
import com.springboot.blog.categories.CategoryRepository;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.utils.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTests {
    @Mock
    private PostRepository postRepository;
    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private ModelMapper mapper;
    @InjectMocks
    private PostServiceImpl postService;
    @BeforeEach
    public void setup(){
      //  postRepository = Mockito.mock(PostRepository.class);
      //  categoryRepository = Mockito.mock(CategoryRepository.class);
      // postService = new PostServiceImpl(postRepository, categoryRepository , mapper);

    }
    @DisplayName("Test for createPost method")
    @Test
    public void givenPost_whenCreatePost_thenShouldCreateAndReturnPost() {
        // given - precondition or setup
        Category category = Category.builder()
                .id(1L)
                .name("Category")
                .description("Category description")
                .build();

        Post post = Post.builder()
                .id(1L)
                .title("Test post")
                .content("Content of the blog")
                .description("Post description")
                .category(category)
                .build();

        given(categoryRepository.findById(category.getId())).willReturn(Optional.of(category));
        // Mock postRepository.save method to return expected post object
        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        given(postRepository.save(postCaptor.capture())).willReturn(post);

        //given(postRepository.save(any(Post.class))).willReturn(post);

        // when - action or behaviour that we are going to test
        PostDto postDto = PostDto.builder()
                .id(1L)
                .title("Test post")
                .content("Content of the blog")
                .description("Post description")
                .categoryId(category.getId())
                .build();

        PostDto savedPost = postService.createPost(postDto);

        // then - verify the outcomes
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(savedPost.getContent()).isEqualTo(post.getContent());
        assertThat(savedPost.getDescription()).isEqualTo(post.getDescription());
        assertThat(savedPost.getCategoryId()).isEqualTo(post.getId());
    }

    @DisplayName("Test for createPost method which throws exception")
    @Test
    public void givenPostWithOutCategory_whenCreatePost_thenShouldNotFound() {
        // given - precondition or setup
        Category category = Category.builder()
                .id(1L)
                .name("Category")
                .description("Category description")
                .build();

        given(categoryRepository.findById(category.getId())).willReturn(Optional.empty());

        // when - action or behaviour that we are going to test
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            PostDto postDto = PostDto.builder()
                    .id(1L)
                    .title("Test post")
                    .content("Content of the blog")
                    .description("Post description")
                    .categoryId(category.getId())
                    .build();

            postService.createPost(postDto);
        });

        String expectedMessage = "Category not found with id : '1'";
        String actualMessage = exception.getMessage();


        // then - verify the outcomes
        assertThat(actualMessage.contains(expectedMessage)).isTrue();
        verify(postRepository, never()).save(any(Post.class));

    }

    @DisplayName("Test for getAllPosts method positive scenario")
    @Test
    public void givenPosts_whenGetAllPosts_thenShouldReturnALlPosts() {
        //given - precondition or setup
        Category category = Category.builder()
                .id(1L)
                .name("Category")
                .description("Category description")
                .build();
        List<Post> posts = Arrays.asList(
                 Post.builder()
                        .id(1L)
                        .title("Test post")
                        .content("Content of the blog")
                        .description("Post description")
                        .category(category)
                        .build(),
                Post.builder()
                        .id(2L)
                        .title("Test post2")
                        .content("Content of the blog2")
                        .description("Post description2")
                        .category(category)
                        .build()
        );
        Page<Post> pagePosts = new PageImpl<>(
                posts,
                PageRequest.of(
                        Integer.parseInt(AppConstants.DEFAULT_PAGE_NUMBER),
                        Integer.parseInt(AppConstants.DEFAULT_PAGE_SIZE)
                ),
                posts.size()
        );

        // when - action or behaviour that we are going to test
        given(postRepository.findAll(any(Pageable.class))).willReturn(pagePosts);

        //then - verify the outcomes
        PostResponse postDtos = postService.getAllPosts(
                Integer.valueOf(AppConstants.DEFAULT_PAGE_NUMBER),
                Integer.valueOf(AppConstants.DEFAULT_PAGE_SIZE),
                AppConstants.DEFAULT_SORT_BY,
                AppConstants.DEFAULT_SORT_DIRECTION
        );

        assertThat(postDtos).isNotNull();
        assertThat(postDtos.getContent().size()).isEqualTo(2);
        assertThat(postDtos.getContent().get(0).getTitle()).isEqualTo("Test post");
    }


    @DisplayName("Test for getAllPosts method negative scenario")
    @Test
    public void givenPosts_whenGetAllPosts_thenShouldReturnEmpty() {
        //given - precondition or setup

        Page<Post> pagePosts = Page.empty();

        // when - action or behaviour that we are going to test
        given(postRepository.findAll(any(Pageable.class))).willReturn(pagePosts);

        //then - verify the outcomes
        PostResponse postDtos = postService.getAllPosts(
                Integer.valueOf(AppConstants.DEFAULT_PAGE_NUMBER),
                Integer.valueOf(AppConstants.DEFAULT_PAGE_SIZE),
                AppConstants.DEFAULT_SORT_BY,
                AppConstants.DEFAULT_SORT_DIRECTION
        );

        assertThat(postDtos).isNotNull();
        assertThat(postDtos.getContent().size()).isEqualTo(0);
    }


    @DisplayName("Test for getPostById method positive scenario")
    @Test
    public void givenPost_whenGetPostById_thenShouldReturnPost() {
        //given - precondition or setup
        Category category = Category.builder()
                .id(1L)
                .name("Category")
                .description("Category description")
                .build();

        Post post =
                Post.builder()
                .id(1L)
                .title("Test post")
                .content("Content of the blog")
                .description("Post description")
                .category(category)
                .build();

        // when - action or behaviour that we are going to test
        given(postRepository.findById(post.getId())).willReturn(Optional.of(post));

        //then - verify the outcomes
        PostDto postDto = postService.getPostById(post.getId());

        assertThat(postDto).isNotNull();
        assertThat(postDto.getId()).isEqualTo(post.getId());
    }


    @DisplayName("Test for getPostById method negative scenario")
    @Test
    public void givenPost_whenGetPostById_thenShouldReturnNullAndThrowsException() {
        //given - precondition or setup
        final Long postId = 1L;

        // when - action or behaviour that we are going to test
        given(postRepository.findById(any(Long.class))).willReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            postService.getPostById(postId);
        });

        String expectedMessage = "Post not found with id : '1'";
        String actualMessage = exception.getMessage();

        // then - verify the outcomes
        assertThat(actualMessage.contains(expectedMessage)).isTrue();
    }

    @DisplayName("Test for Update Post positive scenario")
    @Test
    public void givenPost_whenUpdatePost_thenShouldUpdatePost() {
        //given - precondition or setup
        Category category = Category.builder()
                .id(1L)
                .name("Category")
                .description("Category description")
                .build();

        Post post =
                Post.builder()
                        .id(1L)
                        .title("Test post")
                        .content("Content of the blog")
                        .description("Post description")
                        .category(category)
                        .build();

        // when - action or behaviour that we are going to test
        given(categoryRepository.findById(category.getId())).willReturn(Optional.of(category));
        given(postRepository.findById(post.getId())).willReturn(Optional.of(post));
        given(postRepository.save(any(Post.class))).willReturn(post);

        post.setTitle("Updated title");
        post.setContent("Updated content");
        //then - verify the outcomes
        PostDto postDto =  mapper.map(post, PostDto.class);
        PostDto updatedPost = postService.updatePost(post.getId(), postDto);

        assertThat(updatedPost).isNotNull();
        assertThat(updatedPost.getId()).isEqualTo(post.getId());
        assertThat(updatedPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(updatedPost.getContent()).isEqualTo(post.getContent());
        assertThat(updatedPost.getDescription()).isEqualTo(post.getDescription());

    }


    @DisplayName("Test for Update Post negative scenario")
    @Test
    public void givenPost_whenUpdatePost_thenShouldThrowsNotFoundException() {
        //given - precondition or setup
        Category category = Category.builder()
                .id(1L)
                .name("Category")
                .description("Category description")
                .build();

        Post post =
                Post.builder()
                        .id(1L)
                        .title("Test post")
                        .content("Content of the blog")
                        .description("Post description")
                        .category(category)
                        .build();

        // when - action or behaviour that we are going to test
        given(categoryRepository.findById(category.getId())).willReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            PostDto postDto = mapper.map(post, PostDto.class);
            postService.updatePost(post.getId(), postDto);
        });

        String expectedMessage = "Category not found with id : '1'";
        String actualMessage = exception.getMessage();


        // then - verify the outcomes
        assertThat(actualMessage.contains(expectedMessage)).isTrue();
        verify(postRepository, never()).save(any(Post.class));

    }

    @DisplayName("Delete Post method test")
    @Test
    public void givenPostId_whenDeletePost_thenShouldDeletePost() {
        //given - precondition or setup
        Category category = Category.builder()
                .id(1L)
                .name("Category")
                .description("Category description")
                .build();

        Post post = Post.builder().id(1L)
                .title("Test post")
                .content("Content of the blog")
                .description("Post description")
                .category(category)
                .build();
        given(postRepository.findById(post.getId())).willReturn(Optional.of(post));
        willDoNothing().given(postRepository).deleteById(post.getId());


        // when - action or behaviour that we are going to test
        postService.deletePost(post.getId());

        //then - verify the outcomes
        verify(postRepository, times(1)).deleteById(post.getId());
    }

}
