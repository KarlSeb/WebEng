package application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import qasystem.web.controller.GenericController;
import qasystem.web.controller.QuestionController;
import qasystem.web.controller.UserController;

import static org.assertj.core.api.Assertions.assertThat;

//TODO Funktioniert noch nicht!
/**
 * Testet ob die Applikation startet. 
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {
    @Autowired
    private GenericController genericController;
    @Autowired
    private QuestionController questionController;
    @Autowired
    private UserController userController;

    @Test
    public void contexLoads() throws Exception {
        assertThat(genericController).isNotNull();
        assertThat(questionController).isNotNull();
        assertThat(userController).isNotNull();
    }
}
