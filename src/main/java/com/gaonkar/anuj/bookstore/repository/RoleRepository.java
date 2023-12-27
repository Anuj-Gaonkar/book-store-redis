package com.gaonkar.anuj.bookstore.repository;

import com.gaonkar.anuj.bookstore.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {

}
