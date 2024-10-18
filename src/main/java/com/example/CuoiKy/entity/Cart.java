package com.example.CuoiKy.entity;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Book> books = new ArrayList<>();

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book){
        books.add(book);
    }

    public void removeBook(Long bookId) {
        books.removeIf(book -> book.getId().equals(bookId));
    }

    public void clear() {
        books.clear();
    }
}
