package com.alexander.identityservice;

import com.alexander.enums.RoleType;
import com.alexander.identityservice.model.User;
import com.alexander.identityservice.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class UserDbInitializer {

    @Bean
    ApplicationRunner initReviews(UserRepository userRepository) {
        return args -> {
            User user = new User();
            user.setName("admin");
            user.setEmail("admin@test.com");
            user.setPassword("$2a$10$aCAex9hLLYY6V4Og.UVHi..jWtwb0oSL.2D/bDUoDGem4aW1VPCi6");
            user.setRole(RoleType.ADMIN);
            userRepository.save(user);

            user = new User();
            user.setName("user");
            user.setEmail("user@test.com");
            user.setPassword("$2a$10$aCAex9hLLYY6V4Og.UVHi..jWtwb0oSL.2D/bDUoDGem4aW1VPCi6");
            user.setRole(RoleType.USER);
            userRepository.save(user);

            userRepository.findAll().forEach(System.out::println);
        };
    }

}
