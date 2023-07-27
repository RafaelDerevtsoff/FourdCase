package com.example.service.impl;

import com.example.document.Lesson;
import com.example.dto.CreateLessonsRequest;
import com.example.repository.TeacherRepository;
import com.example.service.LessonsService;
import com.example.service.RabbitMQSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class LessonsServiceImpl implements LessonsService {
    private final TeacherRepository teacherRepository;
    private final RabbitMQSender rabbitTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(TeacherServiceImpl.class);

    public LessonsServiceImpl(TeacherRepository teacherRepository, RabbitMQSender rabbitTemplate) {
        this.teacherRepository = teacherRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Mono<ResponseEntity<String>> createLessons(String teacher, List<Lesson> lessons) {
        return Mono.fromRunnable(() -> rabbitTemplate.send(new CreateLessonsRequest(teacher, lessons))).flatMap(i -> {
            return Mono.just(ResponseEntity.ok().body("Lessons created"));
        }).doOnSuccess(r -> {
            LOGGER.info("Lessons created");
        });
    }
}
