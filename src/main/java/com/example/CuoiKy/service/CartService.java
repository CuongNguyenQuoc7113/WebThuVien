package com.example.CuoiKy.service;


import com.example.CuoiKy.entity.Book;
import com.example.CuoiKy.entity.Cart;
import com.example.CuoiKy.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@SessionScope
public class CartService {
    private Cart cart = new Cart();
    @Autowired
    private IBookRepository bookRepository;
    @Autowired
    private BookService bookService;

    public void addToCart(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Product not found: " + bookId));

        for (Book book1 : cart.getBooks()) {
            if (Objects.equals(book1.getId(), bookId)) {
                return;
            }
        }
        cart.addBook(book);
    }

    public List<Book> getCartItems() {
        return cart.getBooks();
    }

    public void removeFromCart(Long bookId) {
        cart.removeBook(bookId);
    }

    public void clearCart() {
        cart.clear();
    }


}