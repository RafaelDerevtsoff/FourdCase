package com.example.service;

import com.example.document.Lesson;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;

public interface LessonsService {
    Mono<ResponseEntity<String>> createLessons(String teacher, List<Lesson> lessons);
}
