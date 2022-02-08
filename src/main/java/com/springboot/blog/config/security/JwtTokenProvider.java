package com.springboot.blog.config.security;

import com.springboot.blog.config.exception.BlogApiException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    //Retrieve values from .properties
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    // generate token
    public String generateJwtToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        return token;
    }

    // get username from the token
    public String getUsernameFromJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // validate jwt token
    public Boolean validateToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;
        }
        catch(SignatureException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        }
        catch(MalformedJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        }
        catch(ExpiredJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "JWT token expired");
        }
        catch(UnsupportedJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        }
        catch(IllegalArgumentException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Jwt claims string is empty");
        }
    }
}
