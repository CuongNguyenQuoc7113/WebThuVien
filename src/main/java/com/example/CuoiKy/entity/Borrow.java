package com.example.CuoiKy.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="borrow")
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "borrow_date")
    @NotNull(message = "Borrow date is required")
    @Temporal(TemporalType.DATE)
    private Date borrowDate;

    @Column(name = "accept_date")
    @Temporal(TemporalType.DATE)
    private Date acceptDate;

    @OneToMany(mappedBy = "borrow", cascade = CascadeType.ALL)
    private List<BorrowDetail> details;

}
