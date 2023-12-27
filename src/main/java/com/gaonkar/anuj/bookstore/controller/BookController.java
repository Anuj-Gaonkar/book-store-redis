package com.gaonkar.anuj.bookstore.controller;

import com.gaonkar.anuj.bookstore.model.Book;
import com.gaonkar.anuj.bookstore.model.Category;
import com.gaonkar.anuj.bookstore.repository.BookRepository;
import com.gaonkar.anuj.bookstore.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/book")
@Slf4j
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/all")
    public Iterable<Book> getAllBooks(){
        return this.bookRepository.findAll();
    }

    @GetMapping
    public Iterable<Book> getBooks(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                   @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return this.bookRepository.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/{isbn}")
    public Book getBooksByISBN(@PathVariable("isbn") String isbn) {
        return this.bookRepository.findById(isbn).get();
    }

    @GetMapping
    public Iterable<Category> getCategories() {
        return this.categoryRepository.findAll();
    }
}
