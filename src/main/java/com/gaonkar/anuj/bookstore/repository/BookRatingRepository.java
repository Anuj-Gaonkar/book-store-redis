package com.gaonkar.anuj.bookstore.repository;

import com.gaonkar.anuj.bookstore.model.BookRating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRatingRepository extends CrudRepository<BookRating, String>, PagingAndSortingRepository<BookRating, String> {
}
