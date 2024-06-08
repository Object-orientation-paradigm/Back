package com.example.todo.repository;

import com.example.todo.entity.TodoItem;
import com.example.todo.entity.TodoStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
    Page<TodoItem> findByNameContaining(String name, Pageable pageable);
    Page<TodoItem> findByStatus(TodoStatus status, Pageable pageable);

    Page<TodoItem> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<TodoItem> findAllByOrderByCreatedAtAsc(Pageable pageable);

    Page<TodoItem> findByMonth(int month, Pageable pageable);

    long countByStatus(TodoStatus todoStatus);
}
