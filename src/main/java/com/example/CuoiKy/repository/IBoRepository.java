package com.example.CuoiKy.repository;

import com.example.CuoiKy.entity.Bo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBoRepository extends JpaRepository<Bo, Long> {
}
