package com.example.dto;

import com.example.document.Lesson;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonSerialize
@JsonDeserialize
public class CreateLessonsRequest {
    @JsonProperty("teacher")
    private String teacher;
    @JsonProperty("lessons")
    private Map<String,Lesson> lessons;

    public CreateLessonsRequest(String teacher, Map<String, Lesson> lessons) {
        this.teacher = teacher;
        this.lessons = lessons;
    }

    public CreateLessonsRequest() {
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Map<String, Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(HashMap<String, Lesson> lessons) {
        this.lessons = lessons;
    }
}
