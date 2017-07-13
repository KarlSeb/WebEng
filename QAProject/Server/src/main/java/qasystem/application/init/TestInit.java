//package qasystem.application.init;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.annotation.Order;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//import qasystem.QAProjectApplication;
//import qasystem.persistence.entities.Answer;
//import qasystem.persistence.entities.Question;
//import qasystem.persistence.entities.User;
//import qasystem.persistence.repositories.AnswerRepository;
//import qasystem.persistence.repositories.QuestionRepository;
//import qasystem.persistence.repositories.UserRepository;
//import qasystem.web.controller.GenericController;
//import qasystem.web.controller.QuestionController;
//import qasystem.web.dtos.UserDTO;
//
//import java.util.Collection;
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * Beim Start der Applikation wird die run()-Methode dieser Klasse aufgerufen. Diese Klasse dient dabei Testzwecken.
// */
//@Component
//@Order(3)
//@PropertySource("classpath:config.properties")
//public class TestInit implements CommandLineRunner{
//    private final UserRepository repository;
//    private final QuestionRepository qrepo;
//    private final AnswerRepository arepo;
//    private final GenericController genericController;
//    private static final Logger log = LoggerFactory.getLogger(QAProjectApplication.class);
//    private final Environment env;
//
//    @Autowired
//    public TestInit(UserRepository repository, GenericController genericController, QuestionRepository qrepo,
//                    AnswerRepository arepo, Environment env) {
//        this.repository = repository;
//        this.genericController = genericController;
//        this.qrepo = qrepo;
//        this.arepo = arepo;
//        this.env = env;
//    }
//
//    /**
//     * Fügt Testdaten in das UserRepository ein und liest diese danach wieder aus. Der Vorgang wird dabei auf der
//     * Konsole geloggt.
//     */
//    @Override
//    public void run(String... strings) throws Exception{
//        User test = new User("test", "test");
//        test = repository.save(test);
//        Question testQ = new Question("TestQ", "TestQ", test);
//        testQ = qrepo.save(testQ);
//        List<Answer> answers = new LinkedList<>();
//        Answer a1 = new Answer(testQ, "a1", test);
//        Answer a2 = new Answer(testQ, "a2", test);
//        Answer a3 = new Answer(testQ, "a3", test);
//        Answer a4 = new Answer(testQ, "a4", test);
//        answers.add(a1);
//        answers.add(a2);
//        answers.add(a3);
//        answers.add(a4);
//        arepo.save(answers);
//        log.info("INIT DONE");
//        log.info("--------------------------------------------------------");
//        log.info("Fetching all Answers to testQ");
//        Collection<Answer> as = arepo.findAllByParentQuestion(testQ.getId());
//        for (Answer a: as){
//            log.info(a.toString());
//        }
//        log.info("--------------------------------------------------------");
//        log.info("Fetching all Answers to testQ after deleting one Answer");
//        arepo.delete(a3);
//        Collection<Answer> as2 = arepo.findAllByParentQuestion(testQ.getId());
//        for (Answer a: as2){
//            log.info(a.toString());
//        }
//        log.info("--------------------------------------------------------");
//    }
//}
