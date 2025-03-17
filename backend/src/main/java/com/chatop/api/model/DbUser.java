package com.chatop.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;
import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
public class DbUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1", required = true)
    private Integer id;

    @Schema(example = "user@example.com", required = true)
    private String email;

    @Schema(example = "John", required = true)
    private String name;

    @Schema(required = true)
    private String password;

    @Column(name = "created_at", updatable = false)
    @Schema(required = true)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @Schema(required = true)
    private Timestamp updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Timestamp.from(Instant.now());
        updatedAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Timestamp.from(Instant.now());
    }

}
