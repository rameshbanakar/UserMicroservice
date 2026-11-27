package com.example.user.Service;

import com.example.user.Model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Service
public class JWTService {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt-expiry-seconds}")
    private int expirySeconds;


    public String generateToken(User user){
        Date now=new Date();
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .subject(user.getEmail())
                .issuer("Authorization").issuedAt(now)
                .expiration(new Date(now.getTime() + (expirySeconds * 1000L))).signWith(key).compact();

    }

}
