package qasystem.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import qasystem.persistence.entities.Answer;
import qasystem.persistence.entities.Question;
import org.springframework.security.core.userdetails.User;
import qasystem.persistence.repositories.AnswerRepository;
import qasystem.web.dtos.AnswerDTO;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.GregorianCalendar;
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
        if(id==null){
            return new LinkedList<>();
        }
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
    @Secured("ROLE_USER")
    public AnswerDTO answerQuestion(String id, AnswerDTO answerDTO, User user) {
        Question parentQuestion = questionService.getQuestionById(id);
        if(parentQuestion==null){
            return new AnswerDTO();
        }
        qasystem.persistence.entities.User foundUser = userService.getUserByAuthenticationPrinciple(user);
        Answer newAnswer = new Answer(parentQuestion, answerDTO.getText(), foundUser);
        return convertAnswerToDTO(answerRepository.save(newAnswer));
    }

    /**
     * Updated die entsprechenden Datenbankeinträge der angegebenen Antwort und der zugehörigen Frage, indem das Flag
     * zum akzeptieren bzw. beantworrten auf true gesetzt wird-
     *
     * @param id Eindeutiger Identifikator der Frage
     * @param aId EIndeutiger Identifikator der Antwort
     * @param user Der User, der die Antwort akzeptieren will
     */
    @Secured("ROLE_USER")
    public void acceptAnswer(String id, String aId, User user) {
        if(id == null||aId == null){
            throw new IllegalArgumentException("The Ids cannot be null! Given QuestionId: "+id+", given AnswerId: "+aId);
        }
        questionService.authenticateUserForQuestion(id, user);
        questionService.setQuestionToAnswered(id, true);
        Long lAnswerId = Long.parseLong(aId);
        answerRepository.updateAccepted(lAnswerId, true);
    }

    /**
     * Updated die entsprechenden Datenbankeinträge der angegebenen Antwort und der zugehörigen Frage, indem das Flag
     * zum akzeptieren bzw. beantworrten auf true gesetzt wird-
     *
     * @param id Eindeutiger Identifikator der Frage
     * @param aId EIndeutiger Identifikator der Antwort
     * @param user Der User, der die Antwort akzeptieren will
     */
    @Secured("ROLE_USER")
    public void unacceptAnswer(String id, String aId, User user) {
        if(id == null||aId == null){
            throw new IllegalArgumentException("The Ids cannot be null! Given QuestionId: "+id+", given AnswerId: "+aId);
        }
        questionService.authenticateUserForQuestion(id, user);
        questionService.setQuestionToAnswered(id, false);
        Long lAnswerId = Long.parseLong(aId);
        Question question = questionService.getQuestionById(id);
        for (Answer a: question.getAnswers()) {
            if (a.isAccepted()) questionService.setQuestionToAnswered(id, false);
        }
        answerRepository.updateAccepted(lAnswerId, false);
    }

    /**
     * Überprüft ob der übergebene Benutzer die Antwort erstellt hat. Falls dies der Fall ist wir die Antwort aus der
     * Datenbank gelöscht
     *
     * @param id Eindeutiger Identifikator der Frage
     * @param aId Eindeutiger Identifikator der Antwort
     * @param user Benutzer, der die Antwort löschen will
     */
    @Secured("ROLE_USER")
    public void deleteAnswer(String id, String aId, User user) {
        if(id == null||aId == null){
            throw new IllegalArgumentException("The Ids cannot be null! Given QuestionId: "+id+", given AnswerId: "+aId);
        }
        authenticateUserForAnswer(aId, user);
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
        answer.setDate(format(save.getDate()));
        answer.setParentQuestionId(save.getParentQuestion().getId());
        answer.setText(save.getText());
        answer.setUserId(save.getUser().getId());
        answer.setUserName(save.getUser().getUsername());
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
            newDTO.setDate(format(a.getDate()));
            newDTO.setAccepted(a.isAccepted());
            newDTO.setUserName(a.getUser().getUsername());
            answerDTOs.add(newDTO);
        }
        return answerDTOs;
    }

    /**
     * Holt alle Antworten eine bestimmten Benutzers aus der Datenbank und gibt sie zurück.
     *
     * @param id Eindeutiger Identifikator des Benutzers
     * @return Liste aller Antworten, die der Benutzer mit {@code id} gegeben hat.
     */
    Collection<Answer> getAllAnswersByUserId(String id) {
         Long lUserId = Long.parseLong(id);
         return answerRepository.findAllByUser(lUserId);
    }

    void authenticateUserForAnswer(String answerId, User user) {
        qasystem.persistence.entities.User foundUser = userService.getUserByAuthenticationPrinciple(user);
        Long lAnswerId = Long.parseLong(answerId);
        boolean allowed = false;
        for (Answer a : foundUser.getAnswers()) {
            if (a.getId() == lAnswerId) {
                allowed = true;
                break;
            }
        }
        if (!allowed) throw new SecurityException("This User is not allowed to take that action.");
    }

    private static String format(GregorianCalendar calendar){
        SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        fmt.setCalendar(calendar);
        String dateFormatted = fmt.format(calendar.getTime());
        return dateFormatted;
    }
}
