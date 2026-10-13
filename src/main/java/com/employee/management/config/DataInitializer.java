package com.employee.management.config;

import com.employee.management.model.AppUser;
import com.employee.management.model.Role;
import com.employee.management.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;

@Configuration
public class DataInitializer {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner seedAdministrator(AppUserRepository userRepository, PasswordEncoder passwordEncoder,
                                        @Value("${app.bootstrap-admin.email}") String adminEmail,
                                        @Value("${app.bootstrap-admin.password}") String adminPassword) {
        return args -> {
            if (!userRepository.existsByEmailIgnoreCase(adminEmail)) {
                userRepository.save(new AppUser(adminEmail.toLowerCase(Locale.ROOT), passwordEncoder.encode(adminPassword), Role.ROLE_ADMIN));
                log.warn("Bootstrap administrator created for {}. Change its password outside local development.", adminEmail);
            }
        };
    }
}
