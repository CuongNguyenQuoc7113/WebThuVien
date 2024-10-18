package com.example.CuoiKy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 58, message = "Name must be less than 58 characters")
    @NotBlank(message = "Name is required")
    @Column(name = "name", length = 58, nullable = false)
    private String name;

    @Size(max = 258, message = "Description must be less than 258 characters")
    @Column(name = "description", length = 258)
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
}
