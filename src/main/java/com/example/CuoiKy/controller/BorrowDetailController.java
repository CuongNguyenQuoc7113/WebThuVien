package com.example.CuoiKy.controller;

import com.example.CuoiKy.service.BorrowDetailService;
import com.example.CuoiKy.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/brDetail")
public class BorrowDetailController {

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private BorrowDetailService borrowDetailService;

    @GetMapping("/return/{id}")
    public String returnBook(@PathVariable Long id) {
        borrowDetailService.returnBook(id);
        Long brId = borrowDetailService.getBorrowId(id);
        return "redirect:/borrow/detail/" + brId;
    }

    @PostMapping("/return/{id}")
    public String returnBookpost(@PathVariable Long id) {
        borrowDetailService.returnBookpost(id);
        Long brId = borrowDetailService.getBorrowId(id);
        return "redirect:/borrow/detail/" + brId;
    }
}
