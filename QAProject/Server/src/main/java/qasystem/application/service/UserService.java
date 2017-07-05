package qasystem.application.service;

import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * Holt den Benutzer mit der entsprechenden ID aus der Datenbank und gibt diesen zurück.
     *
     * @param id Eindeutiger Identifikator für den Benutzer
     * @return Der Benutzer mit der entsprechenden Id
     */
    User getUserById(long id) {
        return userRepository.findOne(id);
    }

    //TODO
    public List<QuestionDTO> getAllQuestionsOfUser(String id) {
        return null;
    }

    //TODO
    public List<AnswerDTO> getAllAnswersOfUser(String id) {
        return null;
    }
}
