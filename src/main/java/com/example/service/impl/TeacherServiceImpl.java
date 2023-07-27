package com.example.service.impl;

import com.example.document.Teacher;
import com.example.service.RabbitMQSender;
import com.example.service.impl.rabbit.RabbitMQSenderImpl;
import com.example.repository.TeacherRepository;
import com.example.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@EnableReactiveMongoRepositories
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final RabbitMQSender rabbitTemplate;

    @Override
    public Mono<ResponseEntity<String>> createNewTeacher(Teacher teacher) {
        return Mono.fromRunnable(() ->rabbitTemplate.send(teacher))
                .flatMap(t -> Mono.just(ResponseEntity.ok().body("User Created")))
                .doOnSuccess(t -> { Mono.just(ResponseEntity.ok().body("User Created"));})
                .doOnError(e -> Mono.error(new IllegalArgumentException("Unable to create user")));
    }

    @Override
    public Mono<Teacher> findByUsernameAndId(String username, String Id) {
        return teacherRepository.findByUsernameAndId(username, Id);
    }

    @Override
    public Mono<Teacher> findById(String Id) {
        return teacherRepository.findById(Id);
    }

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, RabbitMQSenderImpl rabbitTemplate) {
        this.teacherRepository = teacherRepository;
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return teacherRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found with username: " + username)))
                .map(user -> User.withUsername(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRoles().toArray(new String[0]))
                        .build());
    }

    public Mono<Teacher> findByUsernameTeacher(String username) {
        return teacherRepository.findByUsername(username).switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found with username: " + username)));
    }

}

