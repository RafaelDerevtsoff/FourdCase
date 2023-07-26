package com.example.service.impl;

import com.example.document.Teacher;
import com.example.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

@Service
public class JWTServiceImpl implements JWTService {
    @Autowired
    private SecretKey key;
    @Autowired
    private JwtParser jwtParser;

    @Override
    public String generate(String name, String id) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(name + "," + id)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES)))
                .signWith(key);
        return builder.compact();
    }

    @Override
    public String getNameAndCode(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    @Override
    public boolean validate(Teacher teacher, String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        boolean unexpired = claims.getExpiration().after(Date.from(Instant.now()));
        return unexpired && Objects.equals(teacher.getUsername() + "," + teacher.getId(), claims.getSubject());
    }
}
