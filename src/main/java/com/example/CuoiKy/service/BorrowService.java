package com.example.CuoiKy.service;

import com.example.CuoiKy.entity.*;
import com.example.CuoiKy.repository.IBookRepository;
import com.example.CuoiKy.repository.IBorrowDetailRepository;
import com.example.CuoiKy.repository.IBorrowRepository;
import com.example.CuoiKy.repository.IUserRepository;
import com.example.CuoiKy.utils.Static;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class BorrowService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IBookRepository bookRepository;

    @Autowired
    private IBorrowRepository borrowRepository;

    @Autowired
    private IBorrowDetailRepository borrowDetailRepository;

    @Autowired
    private CartService cartService;

    public void borrowBooks(String userName) {
        if (userName == null) {
            throw new IllegalArgumentException("User Name must not be null");
        }
        User user = userRepository.findByUsername(userName);

        List<Book> books = cartService.getCartItems();
        List<BorrowDetail> details = new ArrayList<>();

        Borrow borrow = new Borrow();
        borrow.setUser(user);
        borrow.setBorrowDate(new Date());

        for (Book book : books) {
            BorrowDetail detail = new BorrowDetail();
            detail.setBorrow(borrow);
            detail.setBook(book);
            // Set other fields of BorrowDetail as necessary
            details.add(detail);
        }
        borrow.setDetails(details);

        borrowRepository.save(borrow);
        cartService.clearCart();
    }

    public List<Borrow> getAllBorrow(){
        return borrowRepository.findAll();
    }

    public Borrow getById(Long id){
        return borrowRepository.findById(id).orElse(null);
    }

    public void acceptBorrow(Long id) {
        Borrow borrow = borrowRepository.findById(id).orElse(null);
        if(borrow!=null) {
            if(borrow.getAcceptDate() == null) {
                borrow.setAcceptDate(new Date());
                for(BorrowDetail detail : borrow.getDetails()) {
                    detail.setReturnDate(Static.dateReturn(new Date(borrow.getBorrowDate().getTime()), detail.getBook().getPage()));
                    borrowDetailRepository.save(detail);
                }
                borrowRepository.save(borrow);
            }
        }
    }

    public List<Borrow> getByUserName(String name){
        return borrowRepository.getByUserName(name);
    }

}
