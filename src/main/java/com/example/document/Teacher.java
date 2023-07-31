package com.example.document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mongodb.lang.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
@JsonSerialize
@Document(collection = "teachers")
public class Teacher {

    @Id
    private String id;
    @Indexed(unique = true)
    @NonNull
    private String username;
    @Indexed(unique = true)
    @NonNull
    private String password;
    private boolean active;

    private List<String> roles;
    private HashMap<String,Lesson> lessons;

    public Teacher() {
    }

    public Teacher(@NonNull String username, @NonNull String password, boolean active, List<String> roles, HashMap<String, Lesson> lessons) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.roles = roles;
        this.lessons = lessons;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public HashMap<String, Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(HashMap<String, Lesson> lessons) {
        this.lessons = lessons;
    }
}

