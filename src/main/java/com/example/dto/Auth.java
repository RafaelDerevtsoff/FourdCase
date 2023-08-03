package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonDeserialize
public class Auth {
    @JsonProperty("username")
    private String username;
    @JsonProperty("token")
    private String token;

    public Auth(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public Auth() {
    }
}
