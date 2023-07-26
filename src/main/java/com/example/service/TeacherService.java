package com.example.service;

import com.example.document.Teacher;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Mono;

public interface TeacherService extends ReactiveUserDetailsService {
    Mono<Teacher> createNewTeacher(Teacher teacher);
    Mono<Teacher> findByUsernameAndId(String username,String Id);
    Mono<Teacher> findById(String Id);
    Mono<Teacher> findByUsernameTeacher(String username);
}
