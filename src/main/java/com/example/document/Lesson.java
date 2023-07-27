package com.example.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
@JsonSerialize
public class Lesson {
    @JsonProperty("title")
    String title;
    @JsonProperty("description")
    String description;
    @JsonProperty("date")
    Date date;

    public Lesson(String title, String description, Date date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }
}
