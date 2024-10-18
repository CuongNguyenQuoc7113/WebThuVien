package com.example.CuoiKy.repository;

import com.example.CuoiKy.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBorrowRepository extends JpaRepository<Borrow, Long> {
    @Query("SELECT br FROM Borrow br WHERE br.user.username  = ?1")
    List<Borrow> getByUserName(String name);
}
