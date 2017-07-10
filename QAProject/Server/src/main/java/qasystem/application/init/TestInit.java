package qasystem.application.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import qasystem.QAProjectApplication;
import qasystem.persistence.entities.Answer;
import qasystem.persistence.entities.Question;
import qasystem.persistence.entities.User;
import qasystem.persistence.repositories.AnswerRepository;
import qasystem.persistence.repositories.QuestionRepository;
import qasystem.persistence.repositories.UserRepository;
import qasystem.web.controller.GenericController;
import qasystem.web.controller.QuestionController;
import qasystem.web.dtos.UserDTO;

import java.util.Collection;

/**
 * Beim Start der Applikation wird die run()-Methode dieser Klasse aufgerufen. Diese Klasse dient dabei Testzwecken.
 */
@Component
@PropertySource("classpath:config.properties")
public class TestInit implements CommandLineRunner{
    private final UserRepository repository;
    private final QuestionRepository qrepo;
    private final AnswerRepository arepo;
    private final GenericController genericController;
    private static final Logger log = LoggerFactory.getLogger(QAProjectApplication.class);
    private final Environment env;

    @Autowired
    public TestInit(UserRepository repository, GenericController genericController, QuestionRepository qrepo,
                    AnswerRepository arepo, Environment env) {
        this.repository = repository;
        this.genericController = genericController;
        this.qrepo = qrepo;
        this.arepo = arepo;
        this.env = env;
    }

    /**
     * Fügt Testdaten in das UserRepository ein und liest diese danach wieder aus. Der Vorgang wird dabei auf der
     * Konsole geloggt.
     */
    @Override
    public void run(String... strings) throws Exception{
        // save a couple of Users
//        if (!env.getProperty("testdataOn", boolean.class)) return; //Geht leider nur wenn String true oder false ist
        String testdataOn = env.getProperty("testdataOn").toLowerCase();
        if (testdataOn.equals("no") || testdataOn.equals("false") || testdataOn.equals("off")) return;
        else if (!(testdataOn.equals("yes") || testdataOn.equals("true") || testdataOn.equals("on"))) {
            System.err.println( "Testdata was turned off. "
                    + "Allowed values for 'testdataOn' in the config.properties file are on/off, yes/no, true/false");
            return;
        }
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

        User bob = new User("bob", "bobspw");
        repository.save(bob);
        User fred = new User("fred", "fredspw");
        repository.save(fred);
        User luke = new User("luke", "lukespw");
        repository.save(luke);
        Question q1 = new Question("title1", "question1?", jack);
        qrepo.save(q1);
        Question q2 = new Question("title2", "question2?", bob);
        qrepo.save(q2);
        Question q3 = new Question("title3", "question3?", bob);
        qrepo.save(q3);
        Question q4 = new Question("title4", "question4?", jack);
        qrepo.save(q4);

        Collection<Question> unanswered = qrepo.findAllByAnsweredNot();
        log.info("All unanswered questions");
        for (Question q: unanswered) {
            log.info(q.toString());
        }
        log.info("Done \n");
        qrepo.updateAnswered(q1.getId(),true);
        log.info("question1 set to answered");
        Collection<Question> unanswered2 = qrepo.findAllByAnsweredNot();
        log.info("Now all unanswered questions");
        for (Question q: unanswered2) {
            log.info(q.toString());
        }
        log.info("Done \n");
        log.info("Creating answers to q1, q2 and q3\n");
        Answer a1= new Answer(q1, "bobs answer to q1!", bob);
        arepo.save(a1);
        Answer a2= new Answer(q2, "lukes answer to q2!", luke);
        arepo.save(a2);
        Answer a3= new Answer(q3, "bobs answer to q3!", bob);
        arepo.save(a3);

        log.info("All questions bob has answered:");
        Collection<Question> answeredByBob = qrepo.findAllByAnswersContainsUserId(bob.getId());
        for (Question q: answeredByBob) {
            log.info(q.toString());
        }
        log.info("Done\n");

    }
}
