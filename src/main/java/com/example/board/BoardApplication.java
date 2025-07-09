package com.example.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class BoardApplication {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application, application-db");
        SpringApplication.run(BoardApplication.class, args);
    }
}
