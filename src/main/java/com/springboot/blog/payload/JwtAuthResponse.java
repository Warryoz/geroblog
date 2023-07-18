package com.springboot.blog.payload;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse {
    @NonNull
    private String accessToken;
    private String tokenType= "Bearer";
}
