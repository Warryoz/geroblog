package com.springboot.blog.security;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse {
    @NotNull
    private String accessToken;
    private String tokenType= "Bearer";
}
