package com.gaonkar.anuj.bookstore.boot;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaonkar.anuj.bookstore.model.Role;
import com.gaonkar.anuj.bookstore.model.User;
import com.gaonkar.anuj.bookstore.repository.RoleRepository;
import com.gaonkar.anuj.bookstore.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@Order(2)
@Slf4j
public class CreateUsers implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            Role admin = roleRepository.findFirstByName("ADMIN");
            Role customer = roleRepository.findFirstByName("CUSTOMER");

            try {
                ObjectMapper mapper = new ObjectMapper();
                TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {
                };
                InputStream inputStream = getClass().getResourceAsStream("/data/users/users.json");
                List<User> users = mapper.readValue(inputStream, typeReference);
                users.forEach(user -> {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    user.addRole(customer);
                    userRepository.save(user);
                });
                log.info(">>> "+users.size() + " Users saved");
            } catch (Exception ex) {
                log.info(">>> Unable to import users: {}", ex.getMessage());
            }

            User adminUser = new User();
            adminUser.setName("Admin");
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword(passwordEncoder.encode(""));
            adminUser.addRole(admin);

            userRepository.save(adminUser);
            log.info(">>> Loaded admin data and customer data");
        }
    }
}
