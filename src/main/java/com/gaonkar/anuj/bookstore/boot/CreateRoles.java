package com.gaonkar.anuj.bookstore.boot;

import com.gaonkar.anuj.bookstore.model.Role;
import com.gaonkar.anuj.bookstore.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@Slf4j
public class CreateRoles implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Role adminRole = Role.builder().name("ADMIN").build();
            Role customerRole = Role.builder().name("CUSTOMER").build();
            roleRepository.save(adminRole);
            roleRepository.save(customerRole);
            log.info(">>> Created admin and customer roles");
        }
    }
}
