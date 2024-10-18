package com.example.CuoiKy.service;

import com.example.CuoiKy.entity.Author;
import com.example.CuoiKy.repository.IAuthorRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private IAuthorRepository authorRepository;

    public List<Author> getAllAuthor(){
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long id){
        return authorRepository.findById(id).orElse(null);
    }

    public Author saveAuthor(Author author){
        return authorRepository.save(author);
    }

    public void updateAuthor(@NotNull Author author) {
        Author existingAuthor = authorRepository.findById(author.getId()).orElseThrow(() -> new IllegalStateException("Category with ID " + author.getId() + " does not exist."));
        existingAuthor.setName(author.getName());
        authorRepository.save(existingAuthor);
    }
    public void deleteAuthor(Long id){
        authorRepository.deleteById(id);
    }
}