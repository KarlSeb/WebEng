package qasystem.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qasystem.persistence.entities.Answer;
import qasystem.persistence.entities.Question;
import qasystem.persistence.entities.User;
import qasystem.persistence.repositories.AnswerRepository;
import qasystem.web.dtos.AnswerDTO;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;

    /**
     * Gibt alle Antworten zu einer übergebenen Frage zurück.
     *
     * @param id Eindeutiger Identifikator der Frage
     * @return Liste aller Antworten, die der Frage mit {@code id} zugeordnet sind.
     */
    public List<AnswerDTO> getAllAnswersByQuestionId(String id) {
        Long lQuestionId = Long.parseLong(id);
        return convertListToDTOs(answerRepository.findAllByParentQuestion(lQuestionId));
    }

    /**
     * Erstellt eine neue Antwort in der Datenbank zu einer übergebenen Frage
     *
     * @param id Eindeutiger Identifiktator der Frage
     * @param answerDTO DTO das die nötigen Daten zum erstellen der Antwort enthält
     * @return AnswerDTO, das die zusätzlichen, automatisch generierten Daten enthält.
     */
    public AnswerDTO answerQuestion(String id, AnswerDTO answerDTO) {
        Question parentQuestion = questionService.getQuestionById(id);
        User u = userService.getUserById(answerDTO.getUserId());
        if(parentQuestion==null||u==null){
            return new AnswerDTO();
        }
        Answer newAnswer = new Answer(parentQuestion, answerDTO.getText(), u);
        return convertAnswerToDTO(answerRepository.save(newAnswer));
    }

    /**
     * Updated die entsprechenden Datenbankeinträge der angegebenen Antwort und der zugehörigen Frage, indem das Flag
     * zum akzeptieren bzw. beantworrten auf true gesetzt wird-
     *
     * @param id Eindeutiger Identifikator der Frage
     * @param aId EIndeutiger Identifikator der Antwort
     */
    public void acceptAnswer(String id, String aId) {
        questionService.setQuestionToAnswered(id, true);
        Long lAnswerId = Long.parseLong(aId);
        answerRepository.updateAccepted(lAnswerId, true);
    }

    /**
     * Überprüft ob der übergebene Benutzer die Antwort erstellt hat. Falls dies der Fall ist wir die Antwort aus der
     * Datenbank gelöscht
     *
     * @param id Eindeutiger Identifikator der Frage
     * @param aId Eindeutiger Identifikator der Antwort
     * @param uId Eindeutiger Identifikator des Benutzers
     */
    public void deleteAnswer(String id, String aId, String uId) {
        //TODO Benutzer überprüfen
        Long lAnswerId = Long.parseLong(aId);
        Answer toDelete = answerRepository.findOne(lAnswerId);
        if (toDelete.isAccepted()){
            questionService.setQuestionToAnswered(id, false);
        }
        answerRepository.delete(toDelete);
    }

    //=======NON-API=======

    /**
     * Konvertiert eine übergebene Antwort zu einem AnswerDTO
     *
     * @param save Antwort, die in ein DTO umgewandelt werden soll
     * @return AnswerDTO mit den Daten der übergebenen Antwort
     */
    private AnswerDTO convertAnswerToDTO(Answer save) {
        if (save == null){
            return new AnswerDTO();
        }
        AnswerDTO answer = new AnswerDTO();
        answer.setId(save.getId());
        answer.setAccepted(save.isAccepted());
        answer.setDate(save.getDate().toString());
        answer.setParentQuestionId(save.getParentQuestion().getId());
        answer.setText(save.getText());
        answer.setUserId(save.getUser().getId());
        return answer;
    }

    /**
     * Löscht alle in der Liste vorhandenen Einträge aus der Datenbank.
     *
     * @param answersToQuestion Liste aller Antworten die aus der Datenbank gelöscht werden sollen
     */
    void deleteAnswerList(Collection<Answer> answersToQuestion) {
        answerRepository.delete(answersToQuestion);
    }

    /**
     * Konvertiert eine übergebene Liste an Antworten in DTOs.
     *
     * @param answers Die Antworten, die in DTOs konvertiert werden sollen
     * @return Liste aller Antworten als DTOs
     */
    List<AnswerDTO> convertListToDTOs(Collection<Answer> answers) {
        if(answers==null){
            return new LinkedList<>();
        }
        List<AnswerDTO> answerDTOs = new LinkedList<>();
        for(Answer a: answers){
            AnswerDTO newDTO = new AnswerDTO();
            newDTO.setId(a.getId());
            newDTO.setText(a.getText());
            newDTO.setUserId(a.getUser().getId());
            newDTO.setParentQuestionId(a.getParentQuestion().getId());
            newDTO.setDate(a.getDate().toString());
            newDTO.setAccepted(a.isAccepted());
            answerDTOs.add(newDTO);
        }
        return answerDTOs;
    }

    //TODO
    Collection<Answer> getAllAnswersByUserId(String id) {
         Long lUserId = Long.parseLong(id);
         return answerRepository.findAllByUser(lUserId);
    }
}
