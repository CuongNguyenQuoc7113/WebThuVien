package com.example.CuoiKy.repository;


import com.example.CuoiKy.entity.Card;
import com.example.CuoiKy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICardRepository extends JpaRepository<Card, Long> {
    Card findByUser(User user);
    boolean existsByUser(User user);

    @Query("SELECT c FROM Card c WHERE c.user.name  = ?1")
    Card getByUserName(String name);
}
