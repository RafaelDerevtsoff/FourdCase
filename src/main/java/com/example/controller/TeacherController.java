package com.example.controller;

import com.example.document.Teacher;
import com.example.dto.ActivateLogin;
import com.example.dto.Auth;
import com.example.dto.LoginDTO;
import com.example.service.JWTService;
import com.example.service.TeacherService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private final Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);
    @Autowired
    private JWTService jwtService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    private PasswordEncoder encoder;


    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestBody LoginDTO teacher) {
        Mono<Teacher> foundUser = teacherService.findByUsernameTeacher(teacher.getUsername());
        return foundUser.mapNotNull(t -> {
                    if (t.getUsername().isEmpty() || t.getId().isEmpty()|| !t.isActive()) {
                        return ResponseEntity.status(404).body("No user found,please register before logging in");
                    }
                    if (encoder.matches(teacher.getPassword(), t.getPassword())) {
                        Auth authResponse  = new Auth(t.getUsername(),jwtService.generate(t.getUsername(), t.getId()));
                        return ResponseEntity.ok().body(authResponse);
                    }
                    return ResponseEntity.badRequest().body("Invalid Credential");
                })
                .onErrorReturn(ResponseEntity.badRequest().body("Invalid Credential"));
    }

    @PostMapping("/create-teacher")
    public Mono<ResponseEntity<String>> createTeacher(@Valid @RequestBody Teacher teacher) {
        teacher.setPassword(encoder.encode(teacher.getPassword()));
        return teacherService.createNewTeacher(teacher);
    }
    @PostMapping("/activate-login")
    public Mono<ResponseEntity<Boolean>> updateTeacher(@RequestBody ActivateLogin activeLogin) {
        return teacherService.activateLogin(activeLogin.getUsername());
    }


}
