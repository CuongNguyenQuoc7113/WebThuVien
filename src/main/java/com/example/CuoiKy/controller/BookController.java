package com.example.CuoiKy.controller;

import com.example.CuoiKy.entity.Author;
import com.example.CuoiKy.entity.Book;
import com.example.CuoiKy.service.AuthorService;
import com.example.CuoiKy.service.BoService;
import com.example.CuoiKy.service.BookService;
import com.example.CuoiKy.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BoService boService;

    @GetMapping("/add")
    public String addBookForm(Model model){
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("authors", authorService.getAllAuthor());
        model.addAttribute("bos", boService.getAllBo());
        return "admin/book/add";
    }

    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("book") Book book, BindingResult result, Model model, @RequestParam("img") MultipartFile image){
        if(result.hasErrors()){
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("authors", authorService.getAllAuthor());
            model.addAttribute("bos", boService.getAllBo());
            return "admin/book/add";
        }

        if (!image.isEmpty()) {
            try {
                Path uploadsPath = Paths.get("image");
                if (!Files.exists(uploadsPath)) {
                    Files.createDirectories(uploadsPath);
                }
                Path file = uploadsPath.resolve(image.getOriginalFilename());
                try (OutputStream os = Files.newOutputStream(file)) {
                    os.write(image.getBytes());
                    os.close();
                }

                book.setImgUrl("/image/" + image.getOriginalFilename());

            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("message", "Lỗi khi lưu hình ảnh");
                return "admin/book/add";
            }
        }
        bookService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping
    public String showAllBook(Model model){
        List<Book> books = bookService.getAllBook();
        model.addAttribute("books", books);
        return "admin/book/list";
    }
    @GetMapping("/edit/{id}")
    public String editBookForm(Model model, @PathVariable Long id) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("authors", authorService.getAllAuthor());
        model.addAttribute("bos", boService.getAllBo());
        return "admin/book/edit";
    }
    @PostMapping("/edit/{id}")
    public String editBook(@Valid @ModelAttribute("book") Book book, Model model, BindingResult result, @RequestParam("img") MultipartFile image, @PathVariable Long id) {
        book.setId(id);
        if(result.hasErrors()){
            model.addAttribute("book", book);
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("authors", authorService.getAllAuthor());
            model.addAttribute("bos", boService.getAllBo());
            return "redirect:/books";
        }
        if (!image.isEmpty()) {
            try {
                Path uploadsPath = Paths.get("image");
                if (!Files.exists(uploadsPath)) {
                    Files.createDirectories(uploadsPath);
                }
                Path file = uploadsPath.resolve(image.getOriginalFilename());
                try (OutputStream os = Files.newOutputStream(file)) {
                    os.write(image.getBytes());
                    os.close();
                }

                book.setImgUrl("/image/" + image.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("message", "Lỗi khi lưu hình ảnh");
                return "redirect:/books";
            }
        }
        else{
            book.setImgUrl(bookService.getBookById(id).getImgUrl());
        }
        bookService.updateBook(book);
        return "redirect:/books";
    }
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable long id, Model model) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }


}
