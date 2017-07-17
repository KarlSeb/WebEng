package qasystem.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qasystem.application.service.UserService;
import qasystem.web.dtos.AnswerDTO;
import qasystem.web.dtos.QuestionDTO;
import qasystem.web.dtos.UserDTO;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * Sucht die Id des angemeldeten Benutzers und gibt diese zurück.
     *
     * @return UserDTO mit der Id und dem Benutzernamen des angemeldeten Nutzers.
     */
    @GetMapping()
    public UserDTO getIdFromUser(@AuthenticationPrincipal User user){
        return userService.getIdFromUser(user);
    }

    /**
     * Gibt eine Liste aller Fragen, die ein bestimmter Benutzer gestellt hat zurück
     *
     * @param id Eindeutiger Identifikator des Benutzers
     * @return Liste aller Fragen, die der Benutzer mit entsprechender {@code id}, gestellt hat.
     */
    @GetMapping(value = "/{id:[0-9]+}/questions")
    public List<QuestionDTO> getAllQuestionsOfUser(@PathVariable("id") String id, @AuthenticationPrincipal User user){
        return userService.getAllQuestionsOfUser(id, user);
    }

    /**
     * Gibt eine Liste aller Antworten, die ein bestimmter Benutzer gegeben hat zurück
     *
     * @param id Eindeutiger Identifikator des Benutzers
     * @return Liste aller Antworten, die der Benutzer mit entsprechender {@code id}, gegeben hat.
     */
    @GetMapping(value = "/{id:[0-9]+}/answers")
    public List<AnswerDTO> getAllAnswersOfUser(@PathVariable("id") String id, @AuthenticationPrincipal User user){
        return userService.getAllAnswersOfUser(id, user);
    }

    /**
     * Gibt eine Liste aller Fragen, auf die der entsprechende Benutzer geantwortet hat.
     *
     * @param id Eindeutiger Identifikator des Benutzers
     * @return Liste aller Fragen, auf die der Benutzer mit {@code id} geantwortet hat
     */
    @GetMapping("/{id:[0-9]+}/answeredQuestions")
    public List<QuestionDTO> getAllQuestionsUserAnswered(@PathVariable("id") String id, @AuthenticationPrincipal User user){
        return userService.getAllQuestionsUserAnswered(id, user);
    }
}
