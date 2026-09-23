package com.phishing.Config;

import com.phishing.Entities.Role;
import com.phishing.Entities.User;
import com.phishing.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AdminBootstrapInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AdminBootstrapInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String name;
    private final String email;
    private final String password;

    public AdminBootstrapInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.admin.name:}") String name,
            @Value("${app.admin.email:}") String email,
            @Value("${app.admin.password:}") String password) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public void run(String... args) {
        if (userRepository.countByRoleAndActiveTrue(Role.ADMIN) > 0) {
            return;
        }

        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            logger.warn("No active administrator exists. Set APP_ADMIN_NAME, APP_ADMIN_EMAIL, and APP_ADMIN_PASSWORD to create the first admin.");
            return;
        }

        if (userRepository.existsByEmail(email)) {
            logger.error("No active administrator exists, but the configured admin email is already in use. Create or reactivate an admin through a trusted database operation.");
            return;
        }

        User admin = new User();
        admin.setName(name);
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRole(Role.ADMIN);
        admin.setCreatedAt(LocalDateTime.now());
        admin.setActive(true);
        userRepository.save(admin);
        logger.info("Initial administrator account created from environment configuration.");
    }
}
