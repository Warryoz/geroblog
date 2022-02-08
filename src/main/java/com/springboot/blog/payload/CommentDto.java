package com.springboot.blog.payload;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ApiModel(description = "Comment model information")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    @ApiModelProperty(value = "Blog comment id")
    private long id;
    //name should not be empty
    @ApiModelProperty(value = "Blog comment name")
    @NotEmpty(message = "Name should not be null or empty")
    private String name;
    //Should not be null or empty

    //Format email
    @ApiModelProperty(value = "Blog comment email")
    @NotEmpty(message = "Email should not be null empty")
    @Email(message = "The email format is invalid")
    private String email;

    // Should not be null or empty
    // At least 10 characters
    @ApiModelProperty(value = "Blog comment body")
    @NotEmpty(message = "Body should not be empty")
    @Size(min = 10, message = "Comment body must be minimum 10 characters")
    private String body;
}
