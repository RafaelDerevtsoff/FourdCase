package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotNull;

@JsonSerialize
@JsonDeserialize
public class LoginDTO {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginDTO() {
    }

    public LoginDTO(@NotNull String username, @NotNull String password) {
        this.username = username;
        this.password = password;
    }
}
