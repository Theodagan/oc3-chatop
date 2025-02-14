package com.chatop.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    private String name;

    private String password;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
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
