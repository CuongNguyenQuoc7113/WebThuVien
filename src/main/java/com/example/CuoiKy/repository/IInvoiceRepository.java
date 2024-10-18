package com.example.CuoiKy.repository;

import com.example.CuoiKy.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInvoiceRepository extends JpaRepository<Invoice, Long> {
}
