package com.example.todo.dto;

public class AuthRequest {
    private String username;
    private String password;

    public AuthRequest() {}

    // 인자 생성자
    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

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

    // Constructors, Getters, and Setters
}
