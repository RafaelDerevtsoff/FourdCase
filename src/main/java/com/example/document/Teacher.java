package com.example.document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mongodb.lang.NonNull;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@JsonSerialize
@JsonDeserialize
@Document(collection = "teachers")
public class Teacher {

    @Id
    private String id;
    @Indexed(unique = true)
    @NonNull
    @NotNull
    private String username;
    @Indexed(unique = true)
    @NonNull
    @NotNull
    private String password;
    private boolean active;
    @NonNull
    @NotNull
    private String email;
    private List<String> roles;

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    private HashMap<String,Lesson> lessons;


    public Teacher(@Nonnull String username, @NotNull String password, boolean active, @NotNull String email, List<String> roles, HashMap<String, Lesson> lessons) {
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
        this.active = active;
        this.email = Objects.requireNonNull(email);
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

