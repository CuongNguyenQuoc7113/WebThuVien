package com.example.CuoiKy.entity;

import com.example.CuoiKy.validator.annotation.ValidUserId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ValidUserId
    private User user;

    @Column(name = "issue_date")
    @NotNull(message = "Issue date is required")
    @Temporal(TemporalType.DATE)
    private Date issueDate;

    @Column(name = "expiry_date")
    @NotNull(message = "Expiry date is required")
    @Temporal(TemporalType.DATE)
    private Date expiryDate;
}
