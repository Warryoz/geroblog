package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Set;


@Schema(description = "Post model information")
@Data
public class PostDto {

    @Schema(description = "Blog post id")
    private Long id;

    // title should not be null or empty
    //title should have at least 2 characters
    @Schema(description = "Blog post title")
    @NotEmpty
    @Size(min = 2, message = "Post title should have 2 characters")
    private String title;

    // title description not be null or empty
    //title description have at least 2 characters
    @Schema(description = "Blog post description")
    @NotEmpty
    @Size(min = 10, message = "Post description should have 10 characters")
    private String description;

    // title content not be null or empty
    @Schema(description = "Blog post content")
    @NotEmpty
    private String content;

    @Schema(description = "Blog post comments")
    private Set<CommentDto> comments;

    @Schema(description = "Blog post category")
    @NotNull(message = "Category is required")
    private Long categoryId;
}
