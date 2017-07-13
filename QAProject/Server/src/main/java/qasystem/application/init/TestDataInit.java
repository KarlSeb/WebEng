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
//        String longString = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla sodales tortor massa, " +
//                "vitae faucibus tortor rhoncus et. Morbi auctor in nulla sed bibendum. Aliquam mattis rhoncus nulla at suscipit.";
//        String longerString = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla sodales tortor massa, " +
//                "vitae faucibus tortor rhoncus et. Morbi auctor in nulla sed bibendum. Aliquam mattis rhoncus nulla at suscipit. " +
//                "Duis consectetur aliquam metus eu condimentum. Suspendisse ligula nisl, elementum eu euismod et, auctor vitae nisl. " +
//                "Pellentesque in dolor a mauris iaculis aliquet. Interdum et malesuada fames ac ante ipsum primis in faucibus. " +
//                "Ut in vehicula tortor. Suspendisse potenti. Nullam ut nulla ex. Sed consequat lorem eu libero feugiat, ornare suscipit erat finibus. " +
//                "Pellentesque risus tortor, dapibus et tempus sed, dictum quis dolor." +
//                "Suspendisse rhoncus orci est, ut tristique urna porttitor vel. Duis lectus dolor, consectetur eget ante in, interdum placerat augue. " +
//                "Etiam volutpat varius dui. Suspendisse odio purus, condimentum eget nisl non, aliquet mattis purus. Nam luctus ex in enim sollicitudin malesuada. " +
//                "Praesent pretium lectus quis venenatis fringilla. Pellentesque condimentum felis ut faucibus tincidunt. Nunc egestas nisi quis tincidunt egestas. " +
//                "Donec vestibulum libero orci, ut fermentum nisi facilisis eu. Mauris semper ante at rhoncus gravida. Donec sit amet gravida dolor. Sed magna lectus, " +
//                "dictum vitae tristique quis, cursus non odio. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. " +
//                "Donec laoreet velit massa, et fermentum neque aliquam sed. In eu lacus porta, facilisis urna ut, varius magna. Donec justo mi, egestas at orci vel, ornare volutpat orci. " +
//                "Interdum et malesuada fames ac ante ipsum primis in faucibus. Nullam vitae tempus nibh. Integer hendrerit eros sed enim fermentum, lobortis tincidunt lacus egestas. " +
//                "Quisque suscipit arcu vitae lorem hendrerit eleifend. Aliquam erat volutpat. Maecenas gravida sit amet nibh mattis bibendum." +
//                "Pellentesque faucibus sed est id eleifend. Nam convallis lobortis accumsan. In maximus felis leo, eu dapibus neque vulputate et. Integer quis lectus at leo volutpat " +
//                "lacinia nec a enim. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam erat volutpat. Quisque tincidunt lorem vel orci ultrices, at venenatis nisi faucibus. " +
//                "Nulla facilisi. Morbi eu nibh bibendum, hendrerit metus at, placerat erat. Suspendisse fermentum nisl vitae ligula convallis vestibulum." +
//                "Aenean et aliquet erat, ut egestas sapien. Vivamus vel nisl scelerisque lacus sodales condimentum. Vestibulum sit amet hendrerit nisl. Nam in tristique velit, vitae tincidunt justo." +
//                " Aenean placerat aliquam lorem at faucibus. In hac habitasse platea dictumst. Sed molestie purus vel nulla fermentum luctus. Vivamus ut porttitor diam, et consectetur mi. " +
//                "Integer faucibus ac odio ut lacinia";
        String longestString = initlongestString();
        Question q4 = new Question(longestString.substring(0,200), longestString, jack);
        qrepo.save(q4);
        log.info("Saving question q4. Title: " + q4.getTitle() + " , user: " + q4.getUser().getUsername());

        Answer a1= new Answer(q1, "bobs answer to q1!", bob);
        arepo.save(a1);
        log.info("Saving answer a1. ParentQuestion: " + a1.getParentQuestion().getTitle() + " , user: " + a1.getUser().getUsername());
        Answer a2= new Answer(q2, "freds answer to q2!", fred);
        arepo.save(a2);
        log.info("Saving answer a2. ParentQuestion: " + a2.getParentQuestion().getTitle() + " , user: " + a2.getUser().getUsername());
        Answer a3= new Answer(q3, "bobs answer to q3! SEHR AUSFÜHRLICH: " + longestString, bob);
        arepo.save(a3);
        log.info("Saving answer a3. ParentQuestion: " + a3.getParentQuestion().getTitle() + " , user: " + a3.getUser().getUsername());
        System.out.println("LOREM IPSUM: Generated 50 paragraphs, 4553 words, 30644 bytes of Lorem Ipsum");//TODO optional
    }

    private String initlongestString() {
        return "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur dui tortor, commodo eget nibh vel, gravida sodales odio. Etiam vel ligula ut lectus semper eleifend id at magna. Aenean lacinia fermentum convallis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam ornare lorem nisl, eget dignissim tortor malesuada et. Donec facilisis gravida risus in dignissim. Nunc nunc dolor, tincidunt id metus in, fermentum molestie diam. Suspendisse vitae mollis felis. Integer turpis justo, malesuada nec aliquam eget, fringilla ac sapien. Vivamus a ultrices metus. Cras porttitor rhoncus sagittis. Cras a ex quis nunc consequat consectetur. Cras nisi ante, dapibus eu lectus feugiat, dictum hendrerit orci. Nunc venenatis diam aliquet dui aliquet, quis congue nisi viverra. Ut feugiat tincidunt sapien, ac porta quam ultricies aliquet. Duis vel ultrices arcu, sed porttitor lacus.\n" +
                "\n" +
                "Nullam luctus tempus diam, quis posuere enim viverra et. Curabitur faucibus, purus in feugiat convallis, augue metus ornare diam, ac posuere orci turpis eget risus. Integer ultricies, risus in pulvinar cursus, odio nunc lacinia risus, eu feugiat tortor sapien vel urna. In hac habitasse platea dictumst. Nunc vestibulum mollis viverra. Vestibulum blandit eu ligula non bibendum. Donec tincidunt dignissim justo, in viverra enim convallis et. Fusce bibendum ipsum in ultricies posuere. Maecenas vestibulum dui vitae tortor iaculis, eget bibendum urna suscipit. Etiam euismod metus ut mi bibendum, in volutpat magna suscipit. Phasellus ut pharetra lacus, quis eleifend velit. Nullam vitae malesuada augue, quis fermentum nunc. Phasellus velit elit, rhoncus nec odio at, condimentum facilisis enim. Nunc in sem consectetur, convallis sem vel, pulvinar orci. Vestibulum neque urna, convallis eu purus vel, suscipit dignissim justo. Pellentesque viverra eleifend lectus id congue.\n" +
                "\n" +
                "Etiam ac egestas ipsum. Donec lobortis ut nulla ac varius. Vivamus libero ligula, porttitor ultrices diam non, fringilla ultricies turpis. Praesent sit amet odio nulla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed fermentum libero sit amet tristique vestibulum. Etiam sit amet tempor nisl, sed tincidunt ipsum.\n" +
                "\n" +
                "Vestibulum commodo elementum libero, commodo rhoncus ex auctor eu. Suspendisse commodo dolor ipsum, id molestie risus tempus a. Etiam scelerisque eget dui et volutpat. Aenean luctus et enim non ornare. In libero tortor, congue id elit id, mollis efficitur sapien. Donec malesuada, nunc et convallis iaculis, ex lorem porta enim, vel viverra lectus ante quis ligula. Fusce malesuada risus lorem. Integer ornare sagittis diam, id venenatis felis luctus sed. Nunc ornare facilisis consequat. Aliquam vestibulum tristique mollis. In hac habitasse platea dictumst.\n" +
                "\n" +
                "Mauris ultricies ac orci in porttitor. Praesent tincidunt porttitor urna. In ut ipsum id felis blandit dictum. Cras condimentum condimentum ipsum, eget gravida sapien. Proin ornare leo dui, a efficitur arcu feugiat a. Nullam laoreet tortor nec ex eleifend rutrum laoreet in massa. Donec a semper purus.\n" +
                "\n" +
                "Praesent nulla eros, faucibus mollis consectetur vitae, sollicitudin non odio. Nunc eu tincidunt tellus. Curabitur luctus luctus elit ut volutpat. Aliquam in ante nibh. Nunc felis dolor, porta non nisl in, sodales blandit tortor. Pellentesque diam sapien, sagittis quis mi ac, sollicitudin viverra augue. Morbi et risus vel eros varius rhoncus vel at tortor. Aliquam aliquam malesuada dictum. Ut enim velit, commodo vitae nibh quis, congue tincidunt tortor. Vivamus interdum vel mauris non condimentum. Nam sit amet dictum ex, sit amet viverra felis. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Praesent varius ligula purus, et fermentum augue maximus ac.\n" +
                "\n" +
                "Cras malesuada, lectus sed scelerisque feugiat, ante lorem mollis felis, commodo feugiat quam lorem ut justo. Aenean vel commodo neque, vitae mollis felis. Donec faucibus, turpis sit amet laoreet eleifend, libero lorem congue mauris, vel eleifend nisi nunc id augue. Praesent nec ex sed augue elementum cursus. Suspendisse euismod dui vitae eleifend finibus. In facilisis sodales molestie. Sed varius magna aliquet mattis tincidunt. Phasellus malesuada sapien ut felis fermentum malesuada ac ut lacus. Etiam ut pretium libero.\n" +
                "\n" +
                "Nunc lacinia, metus quis ullamcorper dictum, lacus massa tincidunt urna, a suscipit nunc mauris sit amet metus. Duis dapibus iaculis est, quis elementum justo feugiat eu. Sed malesuada auctor magna, quis finibus diam eleifend at. Donec imperdiet, diam in consectetur dictum, ligula felis molestie urna, id pulvinar est leo sit amet justo. Mauris augue velit, ullamcorper non quam nec, condimentum semper ex. Mauris et auctor dolor. Donec feugiat eleifend nisl, in condimentum ipsum commodo ac. Aenean imperdiet ante ac augue vehicula fringilla. Ut rutrum faucibus arcu, ac finibus ante auctor eu. Integer rutrum vitae risus id tempor. Suspendisse potenti. Interdum et malesuada fames ac ante ipsum primis in faucibus.\n" +
                "\n" +
                "Etiam vel eleifend lacus. Fusce ac ipsum pellentesque, dapibus magna at, mattis nisl. Nunc sit amet nisi sit amet neque accumsan aliquam. Nam eu felis ut justo facilisis vestibulum non tempus est. Duis non lectus neque. Donec commodo odio magna, nec vehicula arcu tristique a. Phasellus vehicula rutrum metus vitae dapibus. Vestibulum eu massa dictum, aliquam lectus vel, semper felis. Sed massa justo, congue eu rhoncus in, vulputate quis augue. Fusce ornare ipsum elit, non dictum nibh fermentum sit amet. Nulla facilisi. Pellentesque sollicitudin imperdiet convallis. Cras blandit pellentesque finibus.\n" +
                "\n" +
                "Cras dui orci, facilisis in commodo ut, scelerisque semper lectus. Aenean nisl mauris, vestibulum eget lacus et, semper tincidunt eros. Morbi sit amet tellus luctus, accumsan enim at, consequat quam. Ut iaculis ex mauris, at aliquam nisl imperdiet id. Donec mattis, arcu et dignissim finibus, odio sem tempor sapien, quis eleifend felis urna a erat. Aliquam malesuada lorem in tellus finibus, in euismod ipsum imperdiet. Curabitur ultricies lacus sit amet ipsum consectetur, ut convallis neque tempor.\n" +
                "\n" +
                "Aliquam erat volutpat. Aenean vitae mauris gravida nisi aliquam auctor at sit amet nibh. Praesent nunc metus, dictum aliquam tempor ac, imperdiet sed nisi. Suspendisse gravida pharetra sagittis. Cras tincidunt ornare pharetra. Nulla malesuada eleifend posuere. Quisque malesuada ultrices sem, tristique volutpat diam condimentum quis. Nam pretium volutpat nunc, ac dictum mauris congue a. Nullam eu augue sem. Donec at tellus eu orci feugiat vestibulum.\n" +
                "\n" +
                "Etiam nec vulputate est. Sed aliquam sem et magna efficitur, in interdum purus finibus. Praesent facilisis aliquet justo id ultrices. Vivamus lacinia imperdiet nisi, nec malesuada quam. Sed semper ac urna vel ullamcorper. Nulla imperdiet urna sed nisl lacinia vulputate. Quisque a est posuere, molestie nibh eu, rhoncus dui. Quisque vel felis quis magna interdum mollis. Morbi vulputate consequat turpis, et sollicitudin quam rhoncus quis. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aliquam lobortis dui sit amet velit viverra, eget egestas dui efficitur.\n" +
                "\n" +
                "Nullam volutpat orci sit amet mi interdum pretium. Phasellus imperdiet dolor vitae porttitor malesuada. Vestibulum elit nulla, porta eu malesuada quis, commodo eget urna. Donec pellentesque lobortis dignissim. Pellentesque fermentum ornare augue vitae tempor. Mauris ornare, quam nec mollis pharetra, mauris urna molestie est, eu vehicula velit tellus in nibh. Pellentesque efficitur orci lorem, vel semper sem mollis vitae.\n" +
                "\n" +
                "Fusce sapien ipsum, pellentesque vitae rutrum eget, ullamcorper non diam. Morbi vulputate risus ut urna interdum hendrerit. Sed nec rutrum elit. Praesent aliquam leo sem. Nam id imperdiet ex, a facilisis enim. Cras condimentum ante elit, sit amet venenatis nisi accumsan vel. Sed a tellus pretium, commodo lorem sit amet, cursus metus. Fusce vitae nibh egestas, ullamcorper leo lobortis, pulvinar eros. Proin non condimentum turpis, ac iaculis ante. Donec at volutpat urna. In hac habitasse platea dictumst. Ut a consectetur nisl.\n" +
                "\n" +
                "Nunc eget velit erat. Praesent commodo lectus sit amet justo bibendum, in suscipit nibh porta. Sed porttitor diam ex, sit amet accumsan lectus eleifend quis. Quisque congue sit amet sapien quis mollis. Maecenas in eros vel felis ullamcorper malesuada. Curabitur tempus elementum purus at sagittis. Fusce auctor velit nec consectetur vestibulum. Vivamus at neque vitae nisl gravida ultricies luctus ac lorem. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nulla ut suscipit magna, at hendrerit ipsum.\n" +
                "\n" +
                "Pellentesque mattis lorem in tellus blandit, a vulputate risus molestie. Morbi rhoncus leo ac metus tristique auctor. Vestibulum accumsan nunc non eros dapibus, eu aliquet mauris scelerisque. Phasellus posuere, tellus a tristique hendrerit, mauris dui sodales leo, eu faucibus arcu ipsum at arcu. Cras eu congue arcu. Sed sit amet interdum augue. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aliquam erat volutpat. Vestibulum iaculis turpis eget dui gravida faucibus.\n" +
                "\n" +
                "Nam pretium velit sed nulla laoreet, sit amet maximus magna laoreet. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ullamcorper mi nec velit pharetra posuere. Sed eu velit sit amet elit sagittis dictum placerat at orci. Donec et odio eget metus finibus varius sit amet in quam. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nam sit amet blandit nisi, efficitur pharetra purus. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Proin luctus cursus ex et dictum.\n" +
                "\n" +
                "Etiam eleifend mattis nisi, non dapibus nisi commodo eu. Integer urna enim, tincidunt in ipsum auctor, scelerisque gravida lectus. Donec pulvinar volutpat imperdiet. Cras quis erat id erat porttitor ornare. Fusce condimentum sapien elit, et luctus orci congue ut. Maecenas id est ultrices felis eleifend dapibus id ac risus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Curabitur in auctor odio. Nullam congue efficitur elementum. Sed vitae lacus et turpis semper consectetur id dictum erat. In pulvinar suscipit arcu, eget sagittis nisl finibus a. Nam commodo blandit felis, vitae suscipit leo sagittis in. Vivamus id volutpat nunc. Sed sit amet augue orci. Morbi in leo blandit, volutpat elit in, ornare odio.\n" +
                "\n" +
                "Nulla nibh lorem, iaculis vitae fringilla in, scelerisque sed nunc. Sed mi neque, porta eu turpis ac, sagittis venenatis tortor. Nulla facilisi. Quisque tempus porttitor neque vitae convallis. Mauris efficitur arcu ligula, non eleifend orci congue sit amet. Maecenas elementum vitae ante vitae molestie. Ut in nunc tortor. Aliquam volutpat vehicula mauris quis facilisis. Interdum et malesuada fames ac ante ipsum primis in faucibus. Fusce vel maximus diam, ac porta tellus. Integer sed eros eleifend, rutrum nunc quis, elementum nulla. Vestibulum sed blandit nunc. Proin sodales, orci sed tincidunt tristique, ligula ante mollis ipsum, sed luctus eros nibh sed dolor.\n" +
                "\n" +
                "Aenean tempus, nunc vel auctor faucibus, magna lectus ullamcorper turpis, vitae semper elit metus eget leo. Cras cursus metus quam, in pharetra ex pellentesque luctus. Aenean quis turpis lobortis, lobortis nunc quis, dictum nibh. Etiam ut ex ipsum. Phasellus feugiat lectus non egestas imperdiet. Donec in faucibus augue, sed fermentum elit. Quisque erat sapien, vehicula eu luctus in, feugiat ac neque. Mauris et ante nec diam tincidunt dictum. Donec congue vel ligula vel commodo. Curabitur vel ex sem. Quisque tristique ex felis, ut sodales nunc dignissim a. Curabitur arcu risus, dignissim a semper quis, pulvinar in nunc.\n" +
                "\n" +
                "Cras lacinia dolor ut massa lacinia pretium. Nam egestas est at orci lacinia iaculis. Donec id lorem sit amet mi sagittis congue. Nulla molestie mauris at fermentum auctor. Maecenas id odio nec augue varius vestibulum. Integer pharetra, sapien vitae pretium pretium, felis augue venenatis mauris, in varius erat dolor nec velit. Proin lacinia quam sem, a cursus massa faucibus eu. Pellentesque ac mattis nunc. Curabitur blandit vel quam tincidunt ultrices. Etiam at tellus velit. Quisque eu volutpat sem.\n" +
                "\n" +
                "Donec ullamcorper porttitor orci quis aliquam. Mauris bibendum congue magna nec condimentum. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Duis non purus neque. Quisque ac elit risus. Integer eget venenatis arcu. Aenean gravida vulputate auctor. Nunc laoreet ligula dui, eu porttitor metus commodo a. Integer ullamcorper ac augue id finibus. Donec sed ante sed est hendrerit ornare nec sit amet sem. Integer magna felis, ornare eget massa ac, vestibulum egestas nulla. Cras blandit blandit orci, quis ullamcorper risus aliquam non. Vestibulum sed orci nunc.\n" +
                "\n" +
                "Pellentesque mollis lacus libero, a sagittis elit porttitor in. Integer lacinia sem porta, faucibus odio ac, egestas velit. Maecenas pharetra, ex fringilla vestibulum scelerisque, est tellus dictum ligula, vitae hendrerit sem magna sed turpis. Maecenas nec mi auctor arcu cursus suscipit. Nunc ultrices neque ut posuere eleifend. Nunc ut elementum tortor. Aenean quam orci, sollicitudin et luctus vel, ornare quis leo.\n" +
                "\n" +
                "In molestie porttitor urna vitae elementum. Integer ut velit ut neque sagittis sollicitudin. Nam lorem nisi, vulputate non aliquet non, faucibus at elit. Sed faucibus sem a est eleifend vehicula. Interdum et malesuada fames ac ante ipsum primis in faucibus. Aenean faucibus ligula a ante lobortis maximus. Vestibulum vitae tempus lacus, quis dictum metus. Pellentesque accumsan libero ut lorem consequat pellentesque. Integer lobortis pulvinar purus in malesuada. Etiam ac facilisis turpis. Proin at imperdiet odio. Phasellus eu mi ac lorem vestibulum ornare et sit amet mauris. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut blandit mi vitae diam gravida vestibulum.\n" +
                "\n" +
                "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Sed nec nibh id velit feugiat vulputate. In vehicula quam quis eros malesuada, tincidunt euismod dolor euismod. Quisque ut iaculis est, eget mollis magna. Pellentesque vehicula fermentum eros id pulvinar. Etiam euismod est sit amet metus consectetur porttitor. Aliquam a justo ex. Sed eros lacus, interdum id porta vel, malesuada eu sapien. Sed aliquet pulvinar diam, vel dapibus nisl accumsan eget. Nulla facilisi. Nulla facilisi. Sed a pretium dui. Vivamus tincidunt tellus non est convallis pretium.\n" +
                "\n" +
                "Nulla sed euismod erat. Ut non mauris at quam feugiat iaculis. Praesent dictum at magna sit amet consequat. In rutrum magna lorem, eget consectetur lorem egestas id. Sed hendrerit eget lorem sed iaculis. Nulla quis mollis leo. In vel imperdiet massa. Proin velit mi, eleifend at porttitor a, tristique nec metus. Etiam nec mauris a enim sagittis aliquam. Proin mi diam, maximus eget mi ac, ultricies dignissim justo.\n" +
                "\n" +
                "Morbi cursus purus ut sapien luctus, ut sagittis lacus consectetur. Donec et ultricies lorem. Morbi felis est, laoreet vitae orci id, dapibus luctus velit. Vivamus euismod condimentum turpis non placerat. Pellentesque condimentum, eros non blandit iaculis, metus sapien maximus tellus, a faucibus ex augue a leo. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Maecenas ut consectetur ante, quis fringilla ante. Suspendisse lobortis urna ut efficitur egestas. Suspendisse sit amet mattis tellus, et porttitor augue. Etiam ullamcorper odio in odio eleifend porttitor. Etiam nisl mauris, dictum vitae tellus eu, eleifend imperdiet orci. Mauris ac vestibulum orci. Duis congue ante ac consectetur interdum. Pellentesque eget posuere elit, eget ornare elit.\n" +
                "\n" +
                "Mauris suscipit felis auctor ipsum vestibulum, suscipit convallis neque pellentesque. Phasellus rutrum mauris velit, non mollis dolor rhoncus vel. Pellentesque eros ante, lacinia vel euismod placerat, ultricies ut libero. Etiam faucibus dolor maximus justo tempus mollis. Mauris tempor nec est vitae vehicula. Vivamus dignissim sapien nec fermentum ornare. Nunc felis metus, consectetur at purus id, lobortis lobortis tellus. Aenean vitae vestibulum leo, eget posuere nisi. Phasellus id tincidunt ante. Nam hendrerit enim vitae lectus ornare tempus. Sed in dui accumsan, tempor nisi sed, vehicula orci. Nulla eros dui, semper at suscipit dignissim, hendrerit eget quam. Sed volutpat a tellus in feugiat. Integer et condimentum justo, nec pharetra nunc. Maecenas laoreet sollicitudin posuere.\n" +
                "\n" +
                "Duis molestie velit a nunc volutpat pharetra. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque efficitur, justo non porttitor dignissim, felis sem placerat ex, a porta felis quam non nisi. Fusce non consectetur odio. Maecenas eleifend nisl eget rhoncus euismod. Duis ut hendrerit dui, at elementum turpis. Proin quis risus ex. Aliquam blandit, lacus vitae elementum vehicula, lectus nisi scelerisque leo, eu porta turpis ligula ac nulla. Nulla ligula metus, egestas nec nulla non, imperdiet semper dolor. Nullam at augue turpis. Curabitur sem purus, viverra non cursus in, ultrices vitae sem.\n" +
                "\n" +
                "Curabitur eu gravida nibh. Donec vulputate neque quis velit cursus sollicitudin. Nulla non elit non tortor ornare tincidunt vel ut odio. Vestibulum diam justo, tempus vel condimentum quis, euismod nec arcu. Maecenas vehicula arcu vitae leo tempor porta. In hac habitasse platea dictumst. Nam rhoncus accumsan elit. Aliquam vel lacus quis mi ultricies scelerisque eu eu ligula. Donec vel erat est. Nam sit amet varius eros, eu vehicula lorem.\n" +
                "\n" +
                "Nullam placerat feugiat leo in fermentum. Aliquam erat volutpat. Pellentesque hendrerit ornare ipsum sit amet tristique. Pellentesque ut neque at sem tincidunt sodales a a dui. Donec ut ornare neque, ac posuere arcu. Fusce laoreet augue quis lacus tempor iaculis. Donec quis venenatis dolor. Quisque vitae tortor in massa bibendum faucibus sed ac leo. Nulla sagittis, justo non mattis accumsan, massa justo eleifend risus, non pharetra turpis sapien vel nunc. Fusce eu sem tempus, suscipit turpis a, lobortis ipsum. Pellentesque pulvinar hendrerit odio, bibendum bibendum felis vulputate et. Nulla ac hendrerit ante. Quisque dapibus convallis dolor in condimentum. Praesent erat dolor, finibus congue arcu sit amet, blandit tristique neque.\n" +
                "\n" +
                "Mauris ultricies vehicula dui. Suspendisse sed nisi a nisi egestas pretium vel non velit. Duis dignissim pellentesque leo, pellentesque aliquet magna accumsan non. Vestibulum dignissim lacus sit amet eros egestas, nec convallis eros placerat. Nunc varius arcu eget faucibus suscipit. Suspendisse ac lobortis enim, et porttitor nibh. Curabitur dui neque, luctus efficitur arcu a, malesuada euismod augue. Nam a dui egestas, bibendum leo eget, rhoncus nibh. Morbi vitae ullamcorper felis. Mauris diam turpis, cursus a maximus sit amet, sagittis ac lacus. Ut pharetra nunc augue, non dictum ipsum auctor eu. Praesent pellentesque dui ac quam vehicula maximus. Proin laoreet pulvinar ligula et porta. Nam nec leo nisi. Mauris posuere, elit id ultrices fringilla, nunc arcu consectetur metus, et bibendum tortor dui ut nisl. Aliquam eu libero lacinia, viverra nisi sit amet, dignissim risus.\n" +
                "\n" +
                "In ligula mauris, eleifend non pulvinar eget, semper vel enim. Sed bibendum velit sit amet nisi eleifend, non consequat augue accumsan. Vestibulum posuere diam eget felis pharetra sodales. Fusce sed nulla semper purus mollis sagittis eu quis neque. Nulla facilisi. Aenean non turpis euismod, hendrerit erat vitae, mattis eros. Praesent turpis sapien, commodo sit amet nisl vitae, venenatis placerat lacus. Proin eu dolor ullamcorper, aliquam urna a, placerat felis. Curabitur lacus magna, consectetur eget arcu ultrices, efficitur mollis augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Praesent scelerisque lorem sit amet quam tempus rhoncus. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Praesent et laoreet nulla.\n" +
                "\n" +
                "Ut magna lacus, condimentum non pharetra id, fermentum quis nibh. Pellentesque sed lacus sed nibh molestie venenatis sit amet nec magna. Vivamus sed est sapien. Cras vitae nulla nec neque sagittis rutrum. Fusce lobortis, lectus in vehicula finibus, enim mi ultricies tellus, et lobortis enim ex id magna. Integer vitae urna ut lacus pretium sagittis. Ut augue nisl, vehicula volutpat arcu eu, pretium varius tellus. Suspendisse et cursus orci. Proin sagittis sem lectus, id blandit felis commodo vitae. In at est ligula. Aliquam erat volutpat. Aenean maximus nibh eget ante sollicitudin aliquet. Duis lectus est, aliquet congue cursus id, tincidunt quis magna. Duis sed efficitur nisi. Praesent et neque est. Aenean a nulla eget erat vulputate fermentum.\n" +
                "\n" +
                "Mauris dignissim erat nec congue vulputate. Sed feugiat nunc justo, vel interdum ante faucibus rhoncus. Proin ultrices nulla eu lectus facilisis, eu dictum neque ullamcorper. Etiam efficitur metus ut elit consectetur elementum. Pellentesque commodo imperdiet nunc, in ullamcorper massa aliquam vel. Duis at commodo leo. Vivamus dignissim ligula justo, at congue tortor mattis in. Ut at elit diam. Praesent suscipit, ante quis tempor imperdiet, odio neque semper erat, a hendrerit tortor arcu at sem. Duis mattis tellus eu ultrices ultricies.\n" +
                "\n" +
                "Nam ut ipsum imperdiet, dictum massa vel, elementum ligula. Ut luctus consequat orci, a ultricies massa euismod et. Maecenas iaculis lacus vitae sapien lobortis rutrum vel elementum erat. Sed cursus tristique turpis, sed luctus dolor molestie eu. Cras interdum sagittis purus, sit amet mollis ligula bibendum quis. Donec sed justo porttitor velit accumsan porttitor. Nam egestas pellentesque pharetra. Fusce accumsan sed augue et semper. Nunc ante mauris, iaculis vitae finibus at, semper quis nulla. Curabitur volutpat auctor est, ut cursus nibh fermentum et. Nunc vitae pellentesque eros, ut tincidunt tellus. Proin sit amet sem urna. Mauris vitae eros imperdiet, scelerisque purus at, gravida odio.\n" +
                "\n" +
                "Curabitur sed mauris eu urna suscipit porttitor at vitae tellus. Fusce ac lorem sapien. Nunc tempus velit dui, sit amet porta dolor egestas non. Nam at purus vestibulum purus luctus cursus accumsan ac elit. Suspendisse ornare ornare hendrerit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque felis lacus, sodales ut congue nec, varius ac est. Sed condimentum porttitor volutpat. Donec finibus commodo leo sed commodo. Mauris lobortis, quam et pretium finibus, arcu magna condimentum mauris, id mattis metus metus a quam. Nulla tincidunt mauris nulla, et facilisis augue cursus eu. Suspendisse rhoncus, enim ut pharetra porta, eros lacus aliquet purus, at commodo mi magna vitae dui.\n" +
                "\n" +
                "Sed convallis libero rutrum dolor venenatis, non dignissim nisi mollis. Praesent ornare quis dui sit amet sagittis. Fusce condimentum urna eget urna laoreet, vitae malesuada magna faucibus. Curabitur quis faucibus tortor. Phasellus ut mollis libero. Fusce volutpat semper lectus id malesuada. Donec faucibus efficitur ex, eget auctor lorem sagittis a. Donec nec felis eget ante consectetur egestas. Donec eget eros sit amet nisi blandit volutpat. Nullam tempor enim sed est maximus dictum. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nulla eget nulla bibendum metus faucibus tempus vel eu sapien. Quisque ultricies, est faucibus faucibus porttitor, ligula arcu pretium velit, nec vestibulum est sem nec massa. Nullam aliquet tincidunt ligula, convallis semper libero porta vel. Aliquam sit amet molestie justo. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.\n" +
                "\n" +
                "Suspendisse potenti. Suspendisse non vulputate lacus. Nullam in rutrum magna. Suspendisse potenti. Aliquam erat volutpat. Fusce ac nulla eleifend, dictum dui vel, pretium dolor. Mauris vitae felis eleifend, fringilla dolor id, eleifend orci. Quisque ac laoreet tellus. Maecenas dignissim mi vel risus lacinia, et porta felis fermentum. Fusce non lacinia massa. Nulla porttitor libero at ornare malesuada. Pellentesque et sapien non felis varius vulputate eget vitae sem. Curabitur sodales dignissim vestibulum. Pellentesque nec vulputate velit, sed pharetra dolor.\n" +
                "\n" +
                "Praesent vitae dui sed libero luctus convallis ac sed orci. Pellentesque eget placerat leo, id consectetur ex. Nunc eget ipsum auctor, pharetra nisl non, pulvinar risus. Duis fermentum nulla ac ex dapibus iaculis. Nulla facilisi. Nulla sit amet eleifend augue. Vivamus molestie tristique justo, a egestas dolor vestibulum id.\n" +
                "\n" +
                "In venenatis leo a purus vulputate, eu maximus libero consequat. Quisque iaculis ipsum libero, non volutpat tellus sodales quis. Mauris cursus felis lectus, euismod tincidunt eros sodales vel. Integer vel sapien ultrices, tristique lectus vitae, aliquet elit. Integer sagittis congue odio id tempor. Nulla pellentesque eleifend purus, sed dapibus quam faucibus sed. Aliquam imperdiet velit quis tellus fringilla, non elementum est tristique. Aenean et neque fringilla, semper nulla sed, cursus urna. Aliquam sit amet metus lacus. Sed tempus leo consectetur porta ullamcorper. Curabitur mollis vehicula hendrerit. Donec imperdiet sapien nunc, et iaculis eros efficitur quis. Sed ut ipsum a est gravida sagittis vitae in ipsum. Nam pulvinar viverra ligula in elementum. Donec eget tristique diam, a ultricies augue. Morbi mattis urna id volutpat ornare.\n" +
                "\n" +
                "In hac habitasse platea dictumst. Quisque convallis at dui in dignissim. Donec suscipit neque eu sem cursus, facilisis semper lacus gravida. In pharetra odio urna, tempor dapibus lectus sagittis eget. Integer vestibulum semper risus at condimentum. Ut eu commodo tortor. Phasellus semper posuere tortor, id laoreet tortor dapibus eu. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Morbi ut nunc quis nisl euismod fringilla. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Duis viverra nisi at congue pharetra. Sed quis urna ut sem malesuada egestas.\n" +
                "\n" +
                "Aenean quis leo finibus sem faucibus iaculis. Maecenas tristique velit at enim varius pellentesque. In ut lacinia quam. Maecenas vitae lacus id tortor finibus feugiat vel eget purus. Vestibulum metus ipsum, volutpat non massa sed, fermentum porta turpis. Vivamus ac lectus finibus, pretium risus vel, sollicitudin nunc. Morbi lobortis ex enim, eget elementum orci volutpat vitae. Proin congue metus eu ipsum varius, eu pretium tellus sagittis.\n" +
                "\n" +
                "Donec luctus lorem sit amet nunc varius mollis. Fusce in iaculis odio. Cras mattis velit in neque scelerisque, vel tempus erat auctor. Nam posuere neque aliquam ex pulvinar, nec pellentesque mauris interdum. Fusce commodo, nunc non volutpat rhoncus, justo nisi lobortis turpis, at egestas eros lacus sit amet lectus. Donec a semper urna, ac rutrum dolor. Curabitur dolor dui, egestas in turpis posuere, fringilla fringilla magna. Pellentesque quis turpis eu erat ultricies pretium eu et diam. Nunc at enim eros. Phasellus quis turpis arcu. Phasellus a est sit amet orci feugiat tempor. Ut faucibus ante vel nibh condimentum vestibulum. Fusce quis auctor orci, eu aliquam eros. In consectetur rutrum neque ut imperdiet.\n" +
                "\n" +
                "Vestibulum turpis felis, suscipit quis lorem quis, sodales molestie magna. Nulla id tortor odio. Donec auctor ligula nulla, eget sollicitudin nulla dignissim nec. Praesent non turpis nunc. Cras sed arcu vitae metus suscipit tristique. Integer ac mollis turpis. Maecenas in lacinia sem, quis vehicula metus. Morbi aliquam non risus tincidunt consequat. Fusce sed viverra nunc, sed bibendum lacus. Proin ac massa tellus.\n" +
                "\n" +
                "Sed quis arcu hendrerit, mollis nulla id, aliquet leo. Curabitur imperdiet mi quam, vitae iaculis dui venenatis vel. Fusce mattis sapien libero, ac porta sapien suscipit id. Curabitur eu leo varius, auctor lorem sed, ultricies erat. Cras sem velit, sagittis quis risus vel, sagittis feugiat nibh. Vestibulum neque urna, faucibus id ipsum nec, condimentum tristique massa. Nunc vitae efficitur massa.\n" +
                "\n" +
                "Aenean bibendum elit aliquam euismod pretium. Integer convallis sapien sed bibendum molestie. Mauris cursus neque sem. Curabitur finibus orci nec nulla congue porta. Nunc augue neque, rutrum vitae nulla sed, faucibus faucibus urna. Etiam quis leo orci. Aliquam at nunc enim. Phasellus tempus mi a porta pretium. Donec ullamcorper gravida odio sed rutrum. Cras et tincidunt ex. Suspendisse lectus tellus, feugiat et consectetur vitae, eleifend a magna. Donec pellentesque erat dapibus ligula scelerisque, aliquet consectetur dolor bibendum. Vestibulum venenatis magna quis turpis efficitur, ac mattis lorem faucibus. Sed ut auctor purus. Pellentesque ultrices eu quam euismod eleifend. Duis felis nisl, aliquet vel odio vitae, tristique semper turpis.\n" +
                "\n" +
                "Suspendisse odio arcu, tincidunt vitae imperdiet ut, ultricies vitae diam. Maecenas laoreet nibh purus. Donec magna dui, laoreet quis viverra vitae, pulvinar sagittis quam. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Cras a libero et mi sagittis semper. Suspendisse lobortis lobortis diam a sagittis. Integer metus risus, sollicitudin ac auctor vel, fermentum non tellus. Donec tellus nibh, tincidunt nec eros vel, congue convallis risus. Phasellus nec auctor ligula.\n" +
                "\n" +
                "Integer sollicitudin massa et est eleifend, ut vulputate est varius. Vivamus et laoreet nisl. Praesent fringilla nibh sed orci tempor, et vulputate ligula malesuada. Praesent vitae augue turpis. In hac habitasse platea dictumst. Donec aliquam id tellus ut facilisis. Integer et nunc orci. Sed in erat at purus efficitur laoreet in in nisl. Suspendisse ut lorem consequat, eleifend justo in, ullamcorper est. Fusce efficitur mollis dapibus. Phasellus et libero ac nulla rutrum vehicula nec vitae orci. In tincidunt diam vitae convallis gravida.\n" +
                "\n" +
                "Phasellus vitae consectetur sem. Vestibulum eu tellus vel turpis efficitur luctus nec in ex. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus mauris arcu, maximus nec est non, placerat molestie eros. Proin sed orci justo. Maecenas posuere arcu ut magna varius, a eleifend magna ultrices. Aenean pretium massa pulvinar magna porttitor, nec imperdiet metus ultricies. Sed id laoreet sem, quis tincidunt nibh. Vestibulum fermentum mi quis est ullamcorper, ut viverra purus suscipit. Ut odio odio, ultrices id urna id, volutpat ornare turpis.";
    }
}
