package com.example.CuoiKy.repository;

import com.example.CuoiKy.entity.BorrowDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBorrowDetailRepository extends JpaRepository<BorrowDetail, Long> {
    @Query("SELECT bd FROM BorrowDetail bd WHERE bd.borrow.id = ?1")
    List<BorrowDetail> getByBorrowId(Long borrowId);

    @Query("SELECT bd.borrow.id FROM BorrowDetail bd WHERE bd.id = ?1")
    Long getBorrowIdByDetailId(Long id);

    
}
