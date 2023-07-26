package com.example.service.impl;

import com.example.document.Teacher;
import com.example.repository.TeacherRepository;
import com.example.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Service
@EnableReactiveMongoRepositories
public class TeacherServiceImpl implements TeacherService, UserDetails {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

