package com.springboot.blog.service.impl;

import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.SignUpDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public String doLogin(LoginDto loginDto) {
        final var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(), loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    public String signUp(SignUpDto signUpDto) {

        if(userRepository.existsByUsername(signUpDto.getUsername())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Username is already taken");
        }

        if(userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email is already taken");
        }
        //create user object
        User newUser = new User();
        newUser.setName(signUpDto.getName());
        newUser.setEmail(signUpDto.getEmail());
        newUser.setUsername(signUpDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ADMIN").get();
        newUser.setRoles(Collections.singleton(roles));

        userRepository.save(newUser);
        return "User created successfully";

    }

}
