package com.springboot.blog.payload;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class LoginDto {
    @NotEmpty
    @NotNull
    @Size(min = 4, message = "username should have at least 4 characters")
    private String usernameOrEmail;
    @NotEmpty
    @NotNull
    private String password;
}
