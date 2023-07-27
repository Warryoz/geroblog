package com.springboot.blog.auth;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

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
