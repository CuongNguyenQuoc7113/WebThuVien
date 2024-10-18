package com.example.CuoiKy.controller;

import com.example.CuoiKy.entity.Card;
import com.example.CuoiKy.entity.User;
import com.example.CuoiKy.service.CardService;
import com.example.CuoiKy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public String addCardForm(Model model) {
        model.addAttribute("card", new Card());
        return "admin/card/add";
    }

    @GetMapping
    public String showAllCards(Model model) {
        List<Card> cards = cardService.getAllCard();
        model.addAttribute("cards", cards);
        return "admin/card/list";
    }
    @PostMapping("/save")
    public String createLibraryCard(@RequestParam("username") String username,
                                    @RequestParam("months") int months,
                                    Model model) {
        User user = userService.findByUsername(username);
        if (user == null) {
            model.addAttribute("error", "User not found");
            return "admin/card/add";
        }

        Date issueDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issueDate);
        calendar.add(Calendar.MONTH, months);
        Date expiryDate = calendar.getTime();

        Card card = new Card();
        card.setUser(user);
        card.setIssueDate(issueDate);
        card.setExpiryDate(expiryDate);
        cardService.saveCard(card);

        return "redirect:/cards";
    }

    @GetMapping("/renew/{username}")
    public String showRenewForm(@PathVariable("username") String username, Model model) {
        model.addAttribute("username", username);
        return "admin/card/renew";
    }

    @PostMapping("/renew-card")
    public String renewLibraryCard(@RequestParam("months") int months, @RequestParam("username") String username, Model model) {
        User user = userService.findByUsername(username);
        if (user == null) {
            model.addAttribute("error", "Không có tài khoản này.");
            return "admin/card/renew";
        }
        cardService.renewCard(user, months);
        return "redirect:/cards";
    }
}

