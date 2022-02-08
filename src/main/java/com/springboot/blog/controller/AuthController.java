package com.springboot.blog.controller;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.payload.JwtAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.SignUpDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.config.security.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@Api(value = "Auth controller exposes sig-in and sing-up REST APIs")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/sign-in")
    @ApiOperation(value = "REST API to register  or sign-in  user to blog app")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@Valid @RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Get token from token provider
        String token = tokenProvider.generateJwtToken(authentication);

        return ResponseEntity.ok(new JwtAuthResponse(token));
    }

    @PostMapping("sign-up")
    @ApiOperation(value = "REST API to register  or sign-up  user to blog app")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpDto signUpDto){

        // add check for username exists in the db
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        }
        // add check for if email exists in the db
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken", HttpStatus.BAD_REQUEST);
        }

        //create user object
        User newUser = new User();
        newUser.setName(signUpDto.getName());
        newUser.setEmail(signUpDto.getEmail());
        newUser.setUsername(signUpDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        newUser.setRoles(Collections.singleton(roles));

        userRepository.save(newUser);
        return new ResponseEntity<>("User created successfully",HttpStatus.OK);
    }


}
