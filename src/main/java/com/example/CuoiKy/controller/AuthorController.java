package com.example.CuoiKy.controller;

import com.example.CuoiKy.entity.Author;
import com.example.CuoiKy.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {
    @Autowired
    private final AuthorService authorService;


    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("author", new Author());
        return "admin/author/add";
    }

    @PostMapping("/add")
    public String addAuthor(@Valid Author author, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/author/add";
        }
        authorService.saveAuthor(author);
        return "redirect:/authors";
    }

    @GetMapping
    public String listAuthor(Model model) {
        List<Author> authors = authorService.getAllAuthor();
        model.addAttribute("authors", authors);
        return "admin/author/list";
    }


    // GET request to show category edit form
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Author author = authorService.getAuthorById(id);
        //.orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        model.addAttribute("author", author);
        return "admin/author/edit";
    }
    // POST request to update category
    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable("id") Long id, @Valid Author author,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            author.setId(id);
            return "admin/author/edit";
        }
        //categoryService.(category);
        model.addAttribute("authors", authorService.getAllAuthor());
        authorService.updateAuthor(author);
        return "redirect:/authors";
    }
    // GET request for deleting category
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable("id") Long id, Model model) {
        Author author = authorService.getAuthorById(id);
        //.orElseThrow(() -> new IllegalArgumentException("Invalid category Id:"
        //      + id));
        authorService.deleteAuthor(id);
        model.addAttribute("authors", authorService.getAllAuthor());
        return "redirect:/authors";
    }



}