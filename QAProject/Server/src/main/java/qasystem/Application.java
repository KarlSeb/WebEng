package qasystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import qasystem.entities.User;
import qasystem.repositories.UserRepository;

/**
 * Startet das System per Spring Boot.
 */
@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    /**
     * Fügt Testdaten in das UserRepository ein und liest diese danach wieder aus. Der Vorgang wird dabei auf der
     * Konsole geloggt.
     *
     * @param repository UserRepository in das die Daten geschrieben werden.
     */
    @Bean
    public CommandLineRunner demo(UserRepository repository) {
        return (args) -> {
            // save a couple of Users
            repository.save(new User("Jack", "Bauer"));
            repository.save(new User("Chloe", "O'Brian"));
            repository.save(new User("Kim", "Bauer"));
            repository.save(new User("David", "Palmer"));
            repository.save(new User("Michelle", "Dessler"));

            // fetch all Users
            log.info("Users found with findAll():");
            log.info("-------------------------------");
            for (User user : repository.findAll()) {
                log.info(user.toString());
            }
            log.info("");

            // fetch an individual User by ID
            User user = repository.findOne(1L);
            log.info("User found with findOne(1L):");
            log.info("--------------------------------");
            log.info(user.toString());
            log.info("");

            // fetch Users by last name
            log.info("User found with findByUsername('Jack'):");
            log.info("--------------------------------------------");
            User jack = repository.findByUsername("Jack");
            log.info(jack.toString());
            log.info("");
        };
    }
}
