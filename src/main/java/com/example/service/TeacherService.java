package com.example.service;

import com.example.document.Teacher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Mono;

public interface TeacherService extends ReactiveUserDetailsService {
    Mono<ResponseEntity<String>> createNewTeacher(Teacher teacher);
    Mono<Teacher> findByUsernameAndId(String username,String Id);
    Mono<ResponseEntity<Boolean>> activateLogin(String teacher);
    Mono<Teacher> findByUsernameTeacher(String username);
}
