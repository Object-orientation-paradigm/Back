package com.example.todo.dto;

import com.example.todo.entity.TodoItem;
import com.example.todo.entity.TodoStatus;

public class TodoItemDto {

    private String name;
    private TodoStatus status;

    // getters and setters
    public TodoItemDto(String name, TodoStatus status) {
        this.name = name;
        this.status = status;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public void setStatus(TodoStatus status) {
        this.status = status;
    }
}
