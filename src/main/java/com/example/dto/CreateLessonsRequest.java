package com.example.dto;

import com.example.document.Lesson;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
@JsonSerialize
@JsonDeserialize
public class CreateLessonsRequest {
    @JsonProperty("teacher")
    private String teacher;
    @JsonProperty("lessons")
    private List<Lesson> lessons;


    public CreateLessonsRequest(String teacher, List<Lesson> lessons) {
        this.teacher = teacher;
        this.lessons = lessons;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
