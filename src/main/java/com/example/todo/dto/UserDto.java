package com.example.todo.dto;

public class UserDto {
    private String username;
    private String password;
    public UserDto() {}

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
