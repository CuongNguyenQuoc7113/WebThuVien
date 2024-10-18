package com.example.CuoiKy.controller;

import com.example.CuoiKy.entity.Borrow;
import com.example.CuoiKy.entity.BorrowDetail;
import com.example.CuoiKy.entity.Card;
import com.example.CuoiKy.entity.User;
import com.example.CuoiKy.service.BorrowDetailService;
import com.example.CuoiKy.service.BorrowService;
import com.example.CuoiKy.service.CardService;
import com.example.CuoiKy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/borrow")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @Autowired
    private BorrowDetailService borrowDetailService;

    @Autowired
    private CardService cardService;

    @Autowired
    private UserService userService;


    @PostMapping
    public String borrowBooks(@RequestParam("userName") String userName, Model model) {
        Card card = cardService.getByUserName(userName);
        if(card!=null)
        {
            Date expiryDate = card.getExpiryDate();
            if(expiryDate.after(new Date())){
                borrowService.borrowBooks(userName);
                return "redirect:/";
            }
        }
        User user = userService.findByUsername(userName);
        model.addAttribute("user", user);
        model.addAttribute("card", card);
        return "/user/profile";
    }

    @GetMapping
    public String list(Model model) {
        List<Borrow> borrowList = borrowService.getAllBorrow();
        model.addAttribute("borrows", borrowList);
        return "admin/borrow/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Long id) {
        Borrow borrow = borrowService.getById(id);
        model.addAttribute("currentDate", new Date());
        model.addAttribute("borrow", borrow);
        return "admin/borrow/detail";
    }

    @GetMapping("/accept/{id}")
    public String accept(@PathVariable Long id) {
        borrowService.acceptBorrow(id); // Ensure this method updates the accept date
        return "redirect:/borrow/detail/" + id;
    }

    @GetMapping("/fine/{id}")
    public String showFineForm(Model model, @PathVariable Long id) {
        BorrowDetail borrowDetail = borrowDetailService.getById(id);
        model.addAttribute("borrowDetail", borrowDetail);
        return "admin/borrow/detail_fine_form";
    }

    @PostMapping("/fine/{id}")
    public String updateFine(@PathVariable Long id,
                             @RequestParam("fineAmount") Double fineAmount,
                             @RequestParam("description") String description) {
        borrowDetailService.updateFine(id, fineAmount, description);
        return "redirect:/borrow/detail/" + borrowDetailService.getBorrowId(id);
    }

}
