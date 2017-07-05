package qasystem.application.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import qasystem.QAProjectApplication;
import qasystem.persistence.entities.Question;
import qasystem.persistence.entities.User;
import qasystem.persistence.repositories.QuestionRepository;
import qasystem.persistence.repositories.UserRepository;
import qasystem.web.controller.GenericController;
import qasystem.web.dtos.UserDTO;

import java.util.Collection;

/**
 * Beim Start der Applikation wird die run()-Methode dieser Klasse aufgerufen. Diese Klasse dient dabei Testzwecken.
 */
@Component
public class TestInit implements CommandLineRunner{
    private final UserRepository repository;
    private final QuestionRepository qrepo;
    private final GenericController genericController;
    private static final Logger log = LoggerFactory.getLogger(QAProjectApplication.class);

    @Autowired
    public TestInit(UserRepository repository, GenericController genericController, QuestionRepository qrepo) {
        this.repository = repository;
        this.genericController = genericController;
        this.qrepo = qrepo;
    }

    /**
     * Fügt Testdaten in das UserRepository ein und liest diese danach wieder aus. Der Vorgang wird dabei auf der
     * Konsole geloggt.
     */
    @Override
    public void run(String... strings) throws Exception{
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

        // test User registration
        log.info("Register new User with registerNewUser()");
        log.info("--------------------------------------------");
        UserDTO newUser = new UserDTO();
        newUser.setUserName("Max");
        newUser.setPassword("pass");
        genericController.registerNewUser(newUser);
        log.info("Registration done!");
        log.info("");

        //Fetch new registered User
        log.info("User found with findByUsername('Max'):");
        log.info("--------------------------------------------");
        User max = repository.findByUsername("Max");
        log.info(max.toString());
        log.info("");

        qrepo.save(new Question("title1", "question1?", jack));
        Collection<Question> unanswered = qrepo.findAllByAnsweredNot();
        log.info("All unanswered questions");
        for (Question q: unanswered) {
            log.info(q.toString());
        }
        log.info("Done \n");
        qrepo.updateAnswered(1L,true);
        log.info("question set to answered");
        Collection<Question> unanswered2 = qrepo.findAllByAnsweredNot();
        log.info("Now all unanswered questions");
        for (Question q: unanswered2) {
            log.info(q.toString());
        }
        log.info("Done \n");

    }
}
