package com.gaonkar.anuj.bookstore.repository;

import com.gaonkar.anuj.bookstore.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, String> {
}
