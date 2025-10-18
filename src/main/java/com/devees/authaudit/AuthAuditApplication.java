package com.devees.authaudit;

import com.devees.authaudit.entity.User;
import com.devees.authaudit.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class AuthAuditApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthAuditApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            final String adminUsername = "admin";
            final String adminPlainPassword = "12345";

            if (!userRepository.existsByUsername(adminUsername)) {

                User adminUser = User.builder()
                        .username(adminUsername)
                        .email("admin@authaudit.com")
                        .password(passwordEncoder.encode(adminPlainPassword))
                        .roles(Set.of("ROLE_ADMIN", "ROLE_USER"))
                        .build();

                userRepository.save(adminUser);
                System.out.println(">>> Initial 'admin' user created automatically.");
                System.out.println(">>> Username: " + adminUsername + ", Password: " + adminPlainPassword);
            }
        };
    }
}