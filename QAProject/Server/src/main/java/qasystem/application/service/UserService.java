package qasystem.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import qasystem.persistence.entities.User;
import qasystem.persistence.repositories.UserRepository;
import qasystem.web.dtos.AnswerDTO;
import qasystem.web.dtos.QuestionDTO;
import qasystem.web.dtos.UserDTO;

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
     * Konvertiert den Spring-User in eine User-Entity und konvertiert diesen in ein DTO.
     *
     * @return UserDTO mit der Id und dem Benutzernamen des angemeldeten Nutzers.
     */
    public UserDTO getIdFromUser(org.springframework.security.core.userdetails.User user) {
        return convertUserToDTO(getUserByAuthenticationPrinciple(user));
    }

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
    @Secured("ROLE_USER")
    public List<QuestionDTO> getAllQuestionsOfUser(String id, org.springframework.security.core.userdetails.User user) {
        authenticateUser(id, user);
        return questionService.convertListToDTOs(questionService.findAllByUserId(id));
    }

    /**
     * Liefert eine Liste aller Antworten die ein Benutzer gegeben hat
     *
     * @param id Eindeutiger Identifikator des Benutzers
     * @return Liste aller Antworten, die ein bestimmeter Nutzer gegeben hat, als DTOs
     */
    @Secured("ROLE_USER")
    public List<AnswerDTO> getAllAnswersOfUser(String id, org.springframework.security.core.userdetails.User user) {
        authenticateUser(id, user);
        return answerService.convertListToDTOs(answerService.getAllAnswersByUserId(id));
    }

    /**
     * Liefert eine Liste aller Fragen, auf die der übergebene Benutzer geantwortet hat.
     *
     * @param id Eindeutiger Identifikator des Benutzers
     * @return Liste aller Fragen, auf die der Benutzer mit {@code id} geantwortet hat.
     */
    @Secured("ROLE_USER")
    public List<QuestionDTO> getAllQuestionsUserAnswered(String id, org.springframework.security.core.userdetails.User user) {
        authenticateUser(id, user);
        return questionService.findAllQuestionsByAnswerContainsUserId(id);
    }


    User getUserByAuthenticationPrinciple(org.springframework.security.core.userdetails.User user) {
        User foundUser = userRepository.findByUsername(user.getUsername());
        if(foundUser != null)
            return foundUser;
        else
            throw new SecurityException("User that tried to take this action was not found in Database.");
    }

    private void authenticateUser(String id, org.springframework.security.core.userdetails.User user) {
        User foundUser = getUserByAuthenticationPrinciple(user);
        if (foundUser.getId() != Long.parseLong(id))
            throw new SecurityException("This User is not allowed to take that action.");
    }

    private UserDTO convertUserToDTO(User user) {
        UserDTO newDTO = new UserDTO();
        newDTO.setUserName(user.getUsername());
        newDTO.setId(user.getId());
        return newDTO;
    }
}
