package com.example.controller;

import com.example.document.Lesson;
import com.example.document.Teacher;
import com.example.dto.CreateLessonsRequest;
import com.example.service.JWTService;
import com.example.service.LessonsService;
import com.example.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.List;

@RestController
public class LessonsController {
    private Logger LOGGER = LoggerFactory.getLogger(LessonsController.class);
    @Autowired
    private JWTService jwtService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private LessonsService lessonsService;

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody Teacher teacher) {
        Mono<Teacher> foundUser = teacherService.findByUsernameTeacher(teacher.getUsername());
        return foundUser.mapNotNull(t -> {
            if (t.getUsername().isEmpty() || t.getId().isEmpty()) {
                return ResponseEntity.status(404).body("No user found,please register before logging in");
            }
            if (encoder.matches(teacher.getPassword(), t.getPassword())) {
                return ResponseEntity.ok().body(jwtService.generate(t.getUsername(), t.getId()));
            }
            return ResponseEntity.badRequest().body("Invalid Credential");
        });
    }

    @PostMapping("/create-teacher")
    public Mono<ResponseEntity<String>> createTeacher(@RequestBody Teacher teacher) {
        teacher.setPassword(encoder.encode(teacher.getPassword()));
        return teacherService.createNewTeacher(teacher);
    }

    @PostMapping("/create-lessons")
    public Mono<ResponseEntity<CreateLessonsRequest>> createLessons(@RequestHeader("Authorization") String authorizationHeader, @RequestBody List<Lesson> lessons) {
        String teacher = jwtService.getNameAndCode(authorizationHeader.split(" ")[1].trim()).split(",")[0];
        return lessonsService.createLessons(teacher, lessons)
                .thenReturn(ResponseEntity
                        .ok()
                        .body(new CreateLessonsRequest(teacher, lessons)));
    }

    @PutMapping("/update-lesson")
    public Mono<ResponseEntity<CreateLessonsRequest>> updateLesson(@RequestHeader("Authorization") String authorizationHeader,@RequestBody List<Lesson> updateLessons ) {
        String teacher = jwtService.getNameAndCode(authorizationHeader.split(" ")[1].trim()).split(",")[0];
        return lessonsService.updateLessons(teacher,updateLessons);
    }
}
