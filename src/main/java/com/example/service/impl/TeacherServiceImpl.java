package com.example.service.impl;

import com.example.document.Teacher;
import com.example.service.RabbitMQSender;
import com.example.service.impl.rabbit.RabbitMQSenderImpl;
import com.example.repository.TeacherRepository;
import com.example.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final RabbitMQSender rabbitMQSender;
    private final Logger LOGGER = LoggerFactory.getLogger(TeacherServiceImpl.class);

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, RabbitMQSenderImpl rabbitMQSender) {
        this.teacherRepository = teacherRepository;
        this.rabbitMQSender = rabbitMQSender;
    }
    @Override
    public Mono<ResponseEntity<String>> createNewTeacher(Teacher teacher) {
        return Mono.fromRunnable(() -> rabbitMQSender.send(teacher))
                .doOnSuccess(result  -> LOGGER.info("User Created"))
                .thenReturn(ResponseEntity.ok().body("User Created"));
    }

    @Override
    public Mono<Teacher> findByUsernameAndId(String username, String Id) {
        return teacherRepository.findByUsernameAndId(username, Id);
    }

    @Override
    public Mono<ResponseEntity<Boolean>> activateLogin(String teacher) {
        return teacherRepository.findByUsername(teacher)
                .doOnSuccess(t -> {
                    t.setActive(true);
                    teacherRepository.save(t)
                            .doOnSuccess(login -> LOGGER.info("Login successfully activated :  " + t.getUsername()))
                            .subscribe();
                })
                .thenReturn(ResponseEntity.ok().body(true));
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

        return teacherRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found with username: " + username)));
    }
}

