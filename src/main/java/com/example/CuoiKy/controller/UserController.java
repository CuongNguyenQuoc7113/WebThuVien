package com.example.CuoiKy.controller;

import com.example.CuoiKy.entity.Borrow;
import com.example.CuoiKy.entity.Card;
import com.example.CuoiKy.entity.User;
import com.example.CuoiKy.service.BorrowService;
import com.example.CuoiKy.service.CardService;
import com.example.CuoiKy.service.EmailService;
import com.example.CuoiKy.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CardService cardService;
    @Autowired
    private BorrowService borrowService;
    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                model.addAttribute(error.getField() + "_error",
                        error.getDefaultMessage());
            }
            return "user/register";
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        try {
            userService.save(user);
        } catch (RuntimeException e) {
            model.addAttribute("emailError", "There was an error sending the verification email.");
            return "user/register";
        }
        return "redirect:user/login";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "user/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("username") String username, Model model) {
        User user = userService.findByUsername(username);
        if (user == null || !user.isEmailVerified()) {
            model.addAttribute("error", "Username không tồn tại hoặc email chưa được xác thực.");
            return "user/forgot-password";
        }

        String maskedEmail = maskEmail(user.getEmail());
        model.addAttribute("emailMasked", maskedEmail);
        model.addAttribute("username", username);
        return "user/forgot-password";
    }

    @PostMapping("/forgot-password-confirm")
    public String confirmForgotPassword(@RequestParam("username") String username,
                                        @RequestParam("email") String email, Model model) {
        User user = userService.findByUsername(username);
        if (user == null || !user.getEmail().equals(email) || !user.isEmailVerified()) {
            model.addAttribute("error", "Username hoặc email không đúng hoặc email chưa được xác thực.");
            return "user/forgot-password";
        }

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        String resetUrl = "http://localhost:8080/reset-password?token=" + token;
        emailService.sendEmail(user.getEmail(), "Reset your password",
                "Click the link to reset your password: " + resetUrl);

        model.addAttribute("message", "Đã gửi mail về tài khoản " + maskEmail(user.getEmail()));
        return "user/forgot-password-confirm";
    }

    private String maskEmail(String email) {
        int atIndex = email.indexOf("@");
        if (atIndex > 2) {
            return email.substring(0, 2) + "****" + email.substring(atIndex - 4);
        } else {
            return "****" + email.substring(atIndex);
        }
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        String result = userService.validatePasswordResetToken(token);
        if (result != null) {
            model.addAttribute("error", result);
            return "user/forgot-password";
        }

        model.addAttribute("token", token);
        return "user/reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("password") String password, Model model) {
        String result = userService.validatePasswordResetToken(token);
        if (result != null) {
            model.addAttribute("error", result);
            return "user/forgot-password";
        }

        User user = userService.getUserByPasswordResetToken(token);
        if (user != null) {
            userService.changeUserPassword(user, password);
            model.addAttribute("message", "Mật khẩu của bạn đã được thay đổi thành công.");
            return "user/login";
        } else {
            model.addAttribute("error", "Invalid token.");
            return "user/reset-password";
        }
    }

    @GetMapping("/user/profile")
    public String showUserProfile(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        Card card = cardService.findByUser(user);
        model.addAttribute("card", card);
        return "user/profile";
    }

    @PostMapping("/user/create-card")
    public String createLibraryCard(@RequestParam("months") int months, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        cardService.createCard(user, months);
        return "redirect:/user/profile";
    }

    @PostMapping("/user/renew-card")
    public String renewLibraryCard(@RequestParam("months") int months, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        cardService.renewCard(user, months);
        return "redirect:/user/profile";
    }

    @GetMapping("/notification")
    public String showNotification(Model model, Principal principal) {
        List<Borrow> borrows = borrowService.getByUserName(principal.getName());
        model.addAttribute("borrows", borrows);
        return "user/notification";
    }

    @GetMapping("/user/borrowDetail/{id}")
    public String showDetail(Model model, @PathVariable Long id) {
        Borrow borrow = borrowService.getById(id);
        model.addAttribute("borrow", borrow);
        return "user/borrowDetail";
    }
}
