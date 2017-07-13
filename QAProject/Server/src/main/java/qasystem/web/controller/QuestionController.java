package qasystem.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import qasystem.application.service.AnswerService;
import qasystem.application.service.QuestionService;

import qasystem.web.dtos.AnswerDTO;
import qasystem.web.dtos.QuestionDTO;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    @Autowired
    public QuestionController(QuestionService questionService, AnswerService answerService){
        this.questionService = questionService;
        this.answerService = answerService;
    }

    //=======GET-MAPPING=======

    /**
     * Leitet die Anfrage an den Service weiter und gibt das Ergebnis zurück.
     *
     * @return Liste aller im System vorhandenen Fragen in DTOs gekapselt.
     */
    @GetMapping(value = "/all")
    public List<QuestionDTO> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    /**
     * Leitet die Anfrage an den Service weiter und gibt das Ergebnis zurück.
     *
     * @return Liste aller im System vorhandenen, unbeantworteten Fragen in DTOs gekapselt.
     */
    @GetMapping(value = "/unanswered")
    public List<QuestionDTO> getAllUnansweredQuestions(){
        return questionService.getAllUnansweredQuestions();
    }

    /**
     * Leitet die Anfrage an den Service weiter und gibt das Ergebnis zurück.
     *
     * @return Liste aller im System vorhandenen, ungelösten Fragen in DTOs gekapselt.
     */
    @GetMapping(value = "/unsolved")
    public List<QuestionDTO> getAllUnsolvedQuestions(){
        return questionService.getAllUnsolvedQuestions();
    }

    /**
     * Nimmt die in der URL enthaltene Id und gibt diese an die Service-Schicht weiter, um alle Antworten zur übergebenen
     * FragenId zurückzugeben.
     *
     * @return Liste aller im System vorhandenen Antworten für die Frage in DTOs gekapselt.
     */
    @GetMapping(value = "/{id:[1-9]+}/answers")
    public List<AnswerDTO> getAllAnswersByQuestionId(@PathVariable("id") String id){
        return answerService.getAllAnswersByQuestionId(id);
    }

    //=======POST-MAPPING=======
    /**
     * Nimmt die Anfrage entgegen und erstellt für die entsprechende Frage, die per Id identifiziert wird, eine
     * neue Antwort in der Datenbank.
     *
     * @param id Eindeutiger Identifikator für die beantwortete Frage
     * @return Die Antwort als DTO
     */
    @PostMapping(value = "/{id:[1-9]+}/answers")
    @ResponseStatus(HttpStatus.CREATED)
    public AnswerDTO answerQuestion(@PathVariable("id") String id, @Valid @RequestBody AnswerDTO answer,  @AuthenticationPrincipal User user){
        return answerService.answerQuestion(id, answer, user);
    }

    /**
     * Erstellt eine neue Frage im System.
     *
     * @return Die Frage als DTO
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionDTO createQuestion(@Valid @RequestBody QuestionDTO question, @AuthenticationPrincipal User user){
        return questionService.createQuestion(question, user);
    }
    
    //=======PUT-MAPPING=======
    /**
     * Identifiziert die entsprechende Antwort über die eindeutige aId für die Antwort und setzt das entsprechende Flag.
     * Setzt außerdem die Frage, die über die Id  identifiziert wird, auf beantwortet.
     *
     * @param id Eindeutige Id zum identifizieren der Frage
     * @param aId Eindeutige Id zum identifizieren der Antwort
     */
    @PutMapping(value = "/{id:[1-9]+}/answers/{aId:[1-9]+}")
    @ResponseStatus(HttpStatus.OK)
    public void acceptAnswer(@PathVariable("id") String id, @PathVariable("aId") String aId, @AuthenticationPrincipal User user){
        answerService.acceptAnswer(id, aId, user);
    }

    //=======DELETE-MAPPING=======

    /**
     * Löscht die entsprechende Frage, sowie alle Antworten die darunter liegen, falls der übergebene Benutzer der
     * Ersteller der Frage ist.
     *
     * @param id Eindeutiger Identifikator für die Frage
     * @param user Benutzer, der die Antwort löschen will
     */
    @DeleteMapping (value = "/{id:[1-9]+}}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteQuestion(@PathVariable("id") String id, @AuthenticationPrincipal User user){
        questionService.deleteQuestion(id, user);
    }

    /**
     * Löscht die entsprechende Antwort aus dem System, falls der übergebene Benutzer der Ersteller der Frage ist.
     *
     * @param id Eindeutiger Identifikator für die Frage
     * @param aId Eindeutiger Identifikator für die Antwort
     * @param user Benutzer, der die Antwort löschen will
     */
    @DeleteMapping (value = "/{id:[1-9]+}/answers/{aId:[1-9]+}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAnswer(@PathVariable("id") String id, @PathVariable("aId") String aId, @AuthenticationPrincipal User user){
        answerService.deleteAnswer(id, aId, user);
    }
}
