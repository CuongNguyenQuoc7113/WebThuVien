package com.example.CuoiKy.entity;


import com.example.CuoiKy.validator.annotation.ValidCategoryId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
@Entity
@Table(name="book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotEmpty(message = "Title must not be empty")
    @Size(max=200, min=1, message = "Title must be less than 200 characters")
    private String title;

    @Column(name = "img")
    private String imgUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "page")
    private Integer page;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @ValidCategoryId
    private Category category;

    @ManyToOne
    @JoinColumn(name = "bo_id")
    private Bo bo;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}
