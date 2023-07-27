package com.springboot.blog.comments;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(description = "Comment model information")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    @Schema(description = "Blog comment id")
    private Long id;
    //name should not be empty
    @Schema(description = "Blog comment name")
    @NotEmpty(message = "Name should not be null or empty")
    private String name;
    //Should not be null or empty

    //Format email
    @Schema(description = "Blog comment email")
    @NotEmpty(message = "Email should not be null empty")
    @Email(message = "The email format is invalid")
    private String email;

    // Should not be null or empty
    // At least 10 characters
    @Schema(description = "Blog comment body")
    @NotEmpty(message = "Body should not be empty")
    @Size(min = 10, message = "Comment body must be minimum 10 characters")
    private String body;
}
