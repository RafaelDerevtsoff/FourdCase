package com.example.service;

import com.example.document.Teacher;
import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String generate(String name, String id);

    String getNameAndCode(String token);

     boolean validate(Teacher teacher, String token);
}
