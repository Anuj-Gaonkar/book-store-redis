package com.gaonkar.anuj.bookstore.controller;

import com.gaonkar.anuj.bookstore.model.Book;
import com.gaonkar.anuj.bookstore.model.Category;
import com.gaonkar.anuj.bookstore.repository.BookRepository;
import com.gaonkar.anuj.bookstore.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /*@GetMapping
    public Iterable<Book> getBooks(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                   @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return this.bookRepository.findAll(PageRequest.of(page, size));
    }*/

    @GetMapping("/{isbn}")
    public Book getBooksByISBN(@PathVariable("isbn") String isbn) {
        return this.bookRepository.findById(isbn).get();
    }

    @GetMapping("/categories")
    public Iterable<Category> getCategories() {
        return this.categoryRepository.findAll();
    }

    @GetMapping()
    public ResponseEntity<Map<String, Object>> all( //
                                                    @RequestParam(defaultValue = "0") Integer page, //
                                                    @RequestParam(defaultValue = "10") Integer size //
    ) {
        Pageable paging = PageRequest.of(page, size);
        Page<Book> pagedResult = bookRepository.findAll(paging);
        List<Book> books = pagedResult.hasContent() ? pagedResult.getContent() : Collections.emptyList();

        Map<String, Object> response = new HashMap<>();
        response.put("books", books);
        response.put("page", pagedResult.getNumber());
        response.put("pages", pagedResult.getTotalPages());
        response.put("total", pagedResult.getTotalElements());

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
}
