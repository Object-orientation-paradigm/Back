package com.example.todo.config;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

   public int month;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        this.month = this.createdAt.getMonthValue();
    }

    @PreUpdate
    public void preUpdate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        this.month = this.createdAt.getMonthValue();
    }
}
