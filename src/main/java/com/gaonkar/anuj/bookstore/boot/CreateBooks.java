package com.gaonkar.anuj.bookstore.boot;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaonkar.anuj.bookstore.model.Book;
import com.gaonkar.anuj.bookstore.model.Category;
import com.gaonkar.anuj.bookstore.repository.BookRepository;
import com.gaonkar.anuj.bookstore.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Order(3)
@Slf4j
public class CreateBooks implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        if (bookRepository.count() == 0) {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Book>> typeReference = new TypeReference<List<Book>>() {
            };

            List<File> files = Files.list(Paths.get(getClass().getResource("/data/books").toURI()))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".json"))
                    .map(java.nio.file.Path::toFile)
                    .toList();

            Map<String, Category> categories = new HashMap<>();

            files.forEach(file -> {
                try {
                    log.info(">>> Processing Book File: " + file.getPath());
                    String categoryName = file.getName().substring(0, file.getName().lastIndexOf("_"));
                    log.info(">>> Category: " + categoryName);

                    Category category;
                    if (!categories.containsKey(categoryName)) {
                        category = Category.builder().name(categoryName).build();
                        categoryRepository.save(category);
                        categories.put(categoryName, category);
                    } else {
                        category = categories.get(categoryName);
                    }

                    InputStream inputStream = new FileInputStream(file);
                    List<Book> books = mapper.readValue(inputStream, typeReference);
                    books.forEach((book) -> {
                        book.addCategory(category);
                        bookRepository.save(book);
                    });
                    log.info(">>> " + books.size() + " Books Saved!");
                } catch (IOException ex) {
                    log.info("Unable to import books: " + ex.getMessage());
                }
            });
            log.info(">>> Loaded Book Data and Created books...");
        }
    }
}
