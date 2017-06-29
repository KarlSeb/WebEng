package qasystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import qasystem.persistence.entities.User;
import qasystem.persistence.repositories.UserRepository;
import qasystem.web.controller.GenericController;
import qasystem.web.dtos.UserDTO;

/**
 * Startet das System per Spring Boot.
 */
@SpringBootApplication
public class QAProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(QAProjectApplication.class, args);
    }
}
