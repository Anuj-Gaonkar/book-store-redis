package com.gaonkar.anuj.bookstore.repository;

import com.gaonkar.anuj.bookstore.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findFirstByEmail(String email);
}
