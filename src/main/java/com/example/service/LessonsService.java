package com.example.service;

import com.example.document.Lesson;
import com.example.dto.CreateLessonsRequest;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface LessonsService {
    Mono<ResponseEntity<String>> createLessons(String teacher, List<Lesson> lessons);

    Mono<ResponseEntity<CreateLessonsRequest>> updateLessons(String teacher,List<Lesson> updateLessons);

    Flux<Lesson> getAllLessons(String teacher);
}
