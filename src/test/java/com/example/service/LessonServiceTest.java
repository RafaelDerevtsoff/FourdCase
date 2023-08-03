package com.example.service;

import com.example.document.Lesson;
import com.example.document.Teacher;
import com.example.dto.CreateLessonsRequest;
import com.example.dto.UpdateLessonRequest;
import com.example.helper.RedisHelper;
import com.example.repository.TeacherRepository;
import com.example.service.impl.LessonsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LessonsService.class)
public class LessonServiceTest {
    @Mock
    private ReactiveValueOperations<String, Teacher> valueOperations;
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private RabbitMQSender rabbitTemplate;
    @Mock
    private ReactiveRedisTemplate<String, Teacher> redisTemplate;
    @InjectMocks
    private LessonsServiceImpl lessonsService;
    private final Teacher teacher = new Teacher("UserTest",
            "12345",
            false,
            "emailTest",
            List.of(),
            new HashMap<>());
    private final Lesson lesson = new Lesson("test1", "description", Date.from(Instant.now()));
    private final List<Lesson> lessons = List.of(lesson);

    private final Logger log = LoggerFactory.getLogger(LessonServiceTest.class);

    @Test
    public void createLessonsTest() {
        doNothing().when(rabbitTemplate).send(any(CreateLessonsRequest.class));
        StepVerifier.create(lessonsService.createLessons("UserTest", lessons))
                .expectNextMatches(response -> !Objects.requireNonNull(response.getBody()).getLessons().isEmpty())
                .verifyComplete();
    }

    @Test
    public void updateLessonsTest() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.delete(anyString())).thenReturn(Mono.just(true));
        doNothing().when(rabbitTemplate).update(any(UpdateLessonRequest.class));
        StepVerifier.create(lessonsService.updateLessons(teacher.getUsername(), lessons))
                .expectNextMatches(response -> Objects.requireNonNull(response.getBody()).getTeacher().equals(teacher.getUsername()))
                .verifyComplete();
    }
    @Test
    public void getAllLessonsTest(){
        HashMap<String,Lesson> lessonsMap = new HashMap<String,Lesson>();
        lessonsMap.put(lesson.getTitle(),lesson);
        teacher.setLessons(lessonsMap);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(anyString())).thenReturn(Mono.empty());
        when(valueOperations.set(anyString(),any(Teacher.class),any(Duration.class))).thenReturn(Mono.just(true));
        when(teacherRepository.findByUsername(anyString())).thenReturn(Mono.just(teacher));
        Flux<Lesson> lessonFlux = lessonsService.getAllLessons(teacher.getUsername());
        StepVerifier.create(lessonFlux)
                .expectNext(lesson)
                .expectComplete()
                .verify();
    }

}
