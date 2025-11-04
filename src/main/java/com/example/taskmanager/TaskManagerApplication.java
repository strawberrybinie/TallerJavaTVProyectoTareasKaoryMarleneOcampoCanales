package com.example.taskmanager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.taskmanager.model.Role;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.RoleRepository;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TaskManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }

    // Create two users (ADMIN and USER) on start if they don't exist
    @Bean
    CommandLineRunner init(UserRepository userRepo, RoleRepository roleRepo) {
        return args -> {
            BCryptPasswordEncoder enc = new BCryptPasswordEncoder();

            Role rUser = roleRepo.findByName("ROLE_USER").orElseGet(() -> roleRepo.save(new Role(null, "ROLE_USER")));
            Role rAdmin = roleRepo.findByName("ROLE_ADMIN").orElseGet(() -> roleRepo.save(new Role(null, "ROLE_ADMIN")));

            User user = userRepo.findByUsername("user").orElseGet(() ->
                userRepo.save(new User(null, "user", enc.encode("password"), null))
            );
            User admin = userRepo.findByUsername("admin").orElseGet(() ->
                userRepo.save(new User(null, "admin", enc.encode("adminpass"), null))
            );

            // Asigna roles si aún no los tienen
            user.getRoles().add(rUser);
            admin.getRoles().add(rAdmin);
            userRepo.save(user);
            userRepo.save(admin);

            System.out.println("==== Usuarios creados ====");
            System.out.println("USER: " + user.getId() + " -> " + user.getUsername());
            System.out.println("ADMIN: " + admin.getId() + " -> " + admin.getUsername());
            System.out.println("==== Roles creados ====");
            System.out.println("ROLE_USER: " + rUser.getId());
            System.out.println("ROLE_ADMIN: " + rAdmin.getId());
        };
    }
}
