package qasystem.application.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import qasystem.QAProjectApplication;
import qasystem.application.service.QuestionService;
import qasystem.persistence.entities.Answer;
import qasystem.persistence.entities.Question;
import qasystem.persistence.entities.Role;
import qasystem.persistence.entities.User;
import qasystem.persistence.repositories.AnswerRepository;
import qasystem.persistence.repositories.QuestionRepository;
import qasystem.persistence.repositories.RoleRepository;
import qasystem.persistence.repositories.UserRepository;
import qasystem.web.controller.GenericController;
import qasystem.web.controller.QuestionController;
import qasystem.web.dtos.QuestionDTO;

import java.util.Collection;
import java.util.HashSet;

/**
 * Beim Start der Applikation wird die run()-Methode dieser Klasse aufgerufen. Diese Klasse dient dabei Testzwecken.
 */
@Component
@Order(2)
@PropertySource("classpath:config.properties")
public class TestDataInit implements CommandLineRunner{
    private final UserRepository urepo;
    private final QuestionRepository qrepo;
    private final AnswerRepository arepo;
    private final RoleRepository rrepo;
    private final GenericController genericController;
    private final QuestionController questionController;
    private static final Logger log = LoggerFactory.getLogger(QAProjectApplication.class);
    private final Environment env;
    private final QuestionService questionService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TestDataInit(UserRepository urepo, GenericController genericController, QuestionRepository qrepo,
                        AnswerRepository arepo, Environment env, QuestionController questionController, QuestionService questionService,
                        PasswordEncoder passwordencoder, RoleRepository rrepo) {
        this.urepo = urepo;
        this.genericController = genericController;
        this.qrepo = qrepo;
        this.arepo = arepo;
        this.env = env;
        this.questionController = questionController;
        this.questionService = questionService;
        this.passwordEncoder = passwordencoder;
        this.rrepo = rrepo;
    }

    /**
     * Fügt Testdaten in das UserRepository ein und liest diese danach wieder aus. Der Vorgang wird dabei auf der
     * Konsole geloggt.
     */
    @Override
    public void run(String... strings) throws Exception{
        String testdataOn = env.getProperty("testdataOn").toLowerCase();
        if (testdataOn.equals("no") || testdataOn.equals("false") || testdataOn.equals("off")) return;
        else if (!(testdataOn.equals("yes") || testdataOn.equals("true") || testdataOn.equals("on"))) {
            System.err.println( "Testdata was turned off. "
                    + "Allowed values for 'testdataOn' in the config.properties file are on/off, yes/no, true/false");
            return;
        }
        Role userRole = rrepo.findOne("USER");
        HashSet<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        String jackspw = "jackspw";
        User jack = new User("jack", passwordEncoder.encode(jackspw));
        jack.setRoles(userRoles);
        urepo.save(jack);
        log.info("Saving user jack. Username: " + jack.getUsername() + " , password: " + jackspw);
        String chloespw = "chloespw";
        User chloe = new User("chloe", passwordEncoder.encode(chloespw));
        chloe.setRoles(userRoles);
        urepo.save(chloe);
        log.info("Saving user chloe. Username: " + chloe.getUsername() + " , password: " + chloespw);
        String kimspw = "kimspw";
        User kim = new User("kim", passwordEncoder.encode(kimspw));
        kim.setRoles(userRoles);
        urepo.save(kim);
        log.info("Saving user kim. Username: " + kim.getUsername() + " , password: " + kimspw);
        String davidspw = "davidspw";
        User david = new User("david", passwordEncoder.encode(davidspw));
        david.setRoles(userRoles);
        urepo.save(david);
        log.info("Saving user david. Username: " + david.getUsername() + " , password: " + davidspw);
        String michellespw = "michellespw";
        User michelle = new User("michelle", passwordEncoder.encode(michellespw));
        michelle.setRoles(userRoles);
        urepo.save(michelle);
        log.info("Saving user michelle. Username: " + michelle.getUsername() + " , password: " + michellespw);
        String bobspw = "bobspw";
        User bob = new User("bob", passwordEncoder.encode(bobspw));
        bob.setRoles(userRoles);
        urepo.save(bob);
        log.info("Saving user bob. Username: " + bob.getUsername() + " , password: " + bobspw);
        String fredspw = "fredspw";
        User fred = new User("fred", passwordEncoder.encode(fredspw));
        fred.setRoles(userRoles);
        urepo.save(fred);
        log.info("Saving user fred. Username: " + fred.getUsername() + " , password: " + fredspw);
        
        Question q1 = new Question("title1", "question1?", jack);
        qrepo.save(q1);
        log.info("Saving question q1. Title: " + q1.getTitle() + " , user: " + q1.getUser().getUsername());
        Question q2 = new Question("title2", "question2?", bob);
        qrepo.save(q2);
        log.info("Saving question q2. Title: " + q2.getTitle() + " , user: " + q2.getUser().getUsername());
        Question q3 = new Question("title3", "question3?", bob);
        qrepo.save(q3);
        log.info("Saving question q3. Title: " + q3.getTitle() + " , user: " + q3.getUser().getUsername());
        Question q4 = new Question("title4", "question4?", jack);
        qrepo.save(q4);
        log.info("Saving question q4. Title: " + q4.getTitle() + " , user: " + q4.getUser().getUsername());

        Answer a1= new Answer(q1, "bobs answer to q1!", bob);
        arepo.save(a1);
        log.info("Saving answer a1. ParentQuestion: " + a1.getParentQuestion().getTitle() + " , user: " + a1.getUser().getUsername());
        Answer a2= new Answer(q2, "freds answer to q2!", fred);
        arepo.save(a2);
        log.info("Saving answer a2. ParentQuestion: " + a2.getParentQuestion().getTitle() + " , user: " + a2.getUser().getUsername());
        Answer a3= new Answer(q3, "bobs answer to q3!", bob);
        arepo.save(a3);
        log.info("Saving answer a1. ParentQuestion: " + a3.getParentQuestion().getTitle() + " , user: " + a3.getUser().getUsername());
    }
}
