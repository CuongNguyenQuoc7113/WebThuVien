package com.example.CuoiKy.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "invoice_type", nullable = false)
    private String invoiceType; // Loại hóa đơn: "create" (tạo mới) hoặc "renew" (gia hạn)

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "create_date", nullable = false)
    private Date createDate;

    // Constructors, getters, setters
}