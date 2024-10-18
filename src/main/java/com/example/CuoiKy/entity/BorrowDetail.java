package com.example.CuoiKy.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table
public class BorrowDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "borrow_id", nullable = false)
    private Borrow borrow;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "return_date")
    @Temporal(TemporalType.DATE)
    private Date returnDate;

    @Column(name = "actual_return_date")
    @Temporal(TemporalType.DATE)
    private Date actualReturnDate;

    @Column(name = "is_fines")
    private Boolean isFines;

    @Column(name = "description")
    private String description;

    @Column(name = "fine_amount")
    private Double fineAmount;
}
