package com.example.service.impl;

import com.example.document.Lesson;
import com.example.document.Teacher;
import com.example.dto.CreateLessonsRequest;
import com.example.dto.UpdateLessonRequest;
import com.example.repository.TeacherRepository;
import com.example.service.LessonsService;
import com.example.service.RabbitMQSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public Mono<ResponseEntity<CreateLessonsRequest>> createLessons(String teacher, List<Lesson> lessons) {
        Map<String,Lesson> newLessons = lessons.stream().collect(Collectors.toMap(Lesson::getTitle, lesson -> lesson));
        return Mono.fromRunnable(() -> rabbitTemplate.send(new CreateLessonsRequest(teacher, newLessons)))
                .doOnSuccess(r -> {
                    LOGGER.info("Lessons created");
                })
                .thenReturn(ResponseEntity.ok().body(new CreateLessonsRequest(teacher, newLessons)));
    }

    @Override
    public Mono<ResponseEntity<CreateLessonsRequest>> updateLessons(String teacher, List<Lesson> updateLessons) {
        Map<String, Lesson> lessonsUpdatedMap = updateLessons.stream().collect(Collectors.toMap(Lesson::getTitle, person -> person));
        return Mono.fromRunnable(() -> {
                    rabbitTemplate.update(new UpdateLessonRequest(teacher, lessonsUpdatedMap));
                })
                .doOnSuccess(r -> {
                    LOGGER.info("Lessons updated");
                })
                .thenReturn(ResponseEntity.ok().body(new CreateLessonsRequest(teacher, lessonsUpdatedMap)));
    }

    @Override
    public Flux<Lesson> getAllLessons(String teacher) {
        return teacherRepository.findByUsername(teacher).flatMapIterable(t -> t.getLessons().values());
    }
}
