package com.example.service.impl;

import com.example.document.Teacher;
import com.example.repository.TeacherRepository;
import com.example.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@EnableReactiveMongoRepositories
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    @Override
    public Mono<Teacher> createNewTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
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
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
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

