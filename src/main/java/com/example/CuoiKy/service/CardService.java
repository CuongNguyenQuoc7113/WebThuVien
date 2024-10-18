package com.example.CuoiKy.service;

import com.example.CuoiKy.entity.Card;
import com.example.CuoiKy.entity.User;
import com.example.CuoiKy.repository.ICardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CardService {
    @Autowired
    private ICardRepository cardRepository;

    public List<Card> getAllCard(){return cardRepository.findAll();}

    public Card getById(Long id){
        return cardRepository.findById(id).orElse(null);
    }

    public void addCard(Card card){
        cardRepository.save(card);
    }

    public void deleteCardById(Long id){
        cardRepository.deleteById(id);
    }

    public void updateCard(Card card){
        cardRepository.save(card);
    }

    public void createCard(User user, int months) {
        Card card = new Card();
        card.setUser(user);
        card.setIssueDate(new Date());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, months);
        card.setExpiryDate(cal.getTime());
        cardRepository.save(card);
    }

    public void renewCard(User user, int months) {
        Card card = cardRepository.findByUser(user);
        if (card.getExpiryDate().before(new Date())) {
            card.setIssueDate(new Date());
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, months);
            card.setExpiryDate(cal.getTime());
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(card.getExpiryDate());
            cal.add(Calendar.MONTH, months);
            card.setExpiryDate(cal.getTime());
        }
        cardRepository.save(card);
    }

    public Card findByUser(User user) {
        return cardRepository.findByUser(user);
    }

    public boolean hasCard(User user) {
        return cardRepository.existsByUser(user);
    }

    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    public Card getByUserName(String name){
        return cardRepository.getByUserName(name);
    }
}
