package com.example.dto;

import com.example.document.Lesson;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Map;
@JsonDeserialize
@JsonSerialize
public class UpdateLessonRequest {
    @JsonProperty("teacher")
    private String teacher;
    @JsonProperty("lessons")
    private Map<String, Lesson> updatedLessons;

    public UpdateLessonRequest() {
    }

    public UpdateLessonRequest(String teacher, Map<String, Lesson> updatedLessons) {
        this.teacher = teacher;
        this.updatedLessons = updatedLessons;
    }
}
