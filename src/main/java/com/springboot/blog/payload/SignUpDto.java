package com.springboot.blog.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class SignUpDto {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    @Size(min = 4, message = "username should have at least 4 characters")
    private String username;
    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @NotNull
    private String password;
}
