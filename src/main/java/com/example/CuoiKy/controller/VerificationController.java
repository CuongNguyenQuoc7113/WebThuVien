package com.example.CuoiKy.controller;

import com.example.CuoiKy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VerificationController {
    @Autowired
    private UserService userService;

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("username") String username, Model model) {
        boolean isVerified = userService.verifyEmail(username);
        if (isVerified) {
            model.addAttribute("message", "Xác nhận email thành công");
        } else {
            model.addAttribute("message", "Lỗi xác nhận email");
        }
        return "user/verification_result";
    }
}
