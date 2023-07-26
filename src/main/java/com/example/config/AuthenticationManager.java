package com.example.config;

import com.example.document.Teacher;
import com.example.service.JWTService;
import com.example.service.TeacherService;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {
    final JWTService service;
    final TeacherService teacherService;

    public AuthenticationManager(JWTService service, TeacherService teacherService) {
        this.service = service;
        this.teacherService = teacherService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(
                        authentication
                ).cast(BearerToken.class)
                .flatMap(auth -> {
                    String[] user = service.getNameAndCode(auth.getCredentials()).split(",");
                    String username = user[0];
                    String id = user[1];
                    Mono<Teacher> teacher = teacherService.findByUsernameTeacher(username);
                    return teacher.<Authentication>flatMap(t -> {
                        if (service.validate(t, auth.getCredentials())) {
                            auth.setAuthenticated(true);
                            return Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(t.getUsername(), t.getId(), Collections.emptyList()))
                                    ;
                        }
                        Mono.error(new IllegalArgumentException("Invalid Token"));
                        return Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(username, id));
                    });
                });
    }
}
