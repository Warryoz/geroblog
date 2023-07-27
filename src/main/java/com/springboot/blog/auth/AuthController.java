package com.springboot.blog.auth;

import com.springboot.blog.security.JwtAuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@Tag(name = "Auth controller exposes sig-in and sing-up REST APIs")

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = {"/login","/sign-in"})
    @Operation(
            summary =  "REST API to register  or sign-in  user to blog app",
            description = "This API is used to sign-in  user to blog app"
    )
    @ApiResponse(responseCode = "200", description = "User signed-in successfully")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@Valid @RequestBody LoginDto loginDto){
        final var accessToken = authService.doLogin(loginDto);
        var jwtAuthResponse = new JwtAuthResponse(accessToken, "Bearer");
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping(value = {"/sign-up", "/register"})
    @Operation(
            summary = "REST API to register  or sign-up  user to blog app",
            description = "This API is used to sign-up  user to blog app"
    )
    @ApiResponse(responseCode = "200", description = "User signed-up successfully")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpDto signUpDto){
        String response = authService.signUp(signUpDto);
        return ResponseEntity.ok(response);
    }


}
