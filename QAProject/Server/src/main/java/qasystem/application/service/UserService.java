package qasystem.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qasystem.persistence.entities.User;
import qasystem.persistence.repositories.UserRepository;
import qasystem.web.dtos.AnswerDTO;
import qasystem.web.dtos.QuestionDTO;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    /**
     * Holt den Benutzer mit der entsprechenden ID aus der Datenbank und gibt diesen zurück.
     *
     * @param id Eindeutiger Identifikator für den Benutzer
     * @return Der Benutzer mit der entsprechenden Id
     */
    User getUserById(long id) {
        return userRepository.findOne(id);
    }

    /**
     * Liefert eine Liste aller Fragen, die von einem bestimmten Nutzer gestellt wurden.
     *
     * @param id Eindeutiger Identifikator eines Benutzers
     * @return Liste aller Fragen, die von einem Nutzer gestellt wurden, als DTOs
     */
    public List<QuestionDTO> getAllQuestionsOfUser(String id) {
        return questionService.convertListToDTOs(questionService.findAllByUserId(id));
    }

    /**
     * Liefer eine Liste aller Antworten die ein Benutzer gegeben hat
     *
     * @param id Eindeutiger Identifikator des Benutzers
     * @return Liste aller Antworten, die ein bestimmeter Nutzer gegeben hat, als DTOs
     */
    public List<AnswerDTO> getAllAnswersOfUser(String id) {
        return answerService.convertListToDTOs(answerService.getAllAnswersByUserId(id));
    }

    //TODO
    public List<QuestionDTO> getAllQuestionsUserAnswered(String id) {
        return null;
    }
}
