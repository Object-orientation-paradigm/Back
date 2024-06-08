package com.example.todo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResponse {
    private String token;

    // 기본 생성자
    public AuthResponse() {}

    @JsonCreator
    public AuthResponse(@JsonProperty("token") String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    // Setter 메소드 추가 (Optional)
    public void setToken(String token) {
        this.token = token;
    }
}
