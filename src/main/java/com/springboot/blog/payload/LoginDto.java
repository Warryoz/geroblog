package com.springboot.blog.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
