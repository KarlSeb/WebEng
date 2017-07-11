package qasystem.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import qasystem.persistence.entities.Answer;
import qasystem.persistence.entities.Question;
import qasystem.persistence.entities.User;
import qasystem.persistence.repositories.QuestionRepository;
import qasystem.web.dtos.QuestionDTO;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private UserService userService;

    //=======API=======

    /**
     * Holt alle im System vorhandenen Fragen aus der Datenbank und gibt diese als DTOs zurück.
     *
     * @return Liste aus QuestionDTOs aller Fragen
     */
    public List<QuestionDTO> getAllQuestions() {
        List<Question> all = questionRepository.findAll();
        return convertListToDTOs(all);
    }

    /**
     * Liefert eine Liste aller Fragen, die noch als unbeantwortet gelten.
     *
     * @return Liste aller Fragen, die keine akzeptierte Antwort besitzen
     */
    public List<QuestionDTO> getAllUnsolvedQuestions() {
        return convertListToDTOs(questionRepository.findAllByAnsweredNot());
    }

    /**
     * Liefert eine Liste aller Fragen, die noch keine Antwort haben.
     *
     * @return Liste aller Fragen, denen noch keine Antwort zugeordnet ist.
     */
    public List<QuestionDTO> getAllUnansweredQuestions() {
        return convertListToDTOs(questionRepository.findAllByNoReply());
    }

    /**
     * Erstellt eine neue Frage im System.
     *
     * @return Die Frage als DTO
     */
    @Secured("ROLE_USER")
    public QuestionDTO createQuestion(QuestionDTO newQuestion) {
        final Question question = convertDTOToQuestion(newQuestion);
        return convertQuestionToDTO(questionRepository.save(question));
    }

    /**
     * Löscht die Frage, die durch {@code id} identifiziert wird, und alle darunter liegenden Antworten, falls der
     * User, der mittels {@code uId} identifiziert wird, der Ersteller der Frage ist.
     *
     * @param id  Eindeutiger Identifikator für die Frage
     * @param uId Eindeutiger Identifikator für die Frage
     */
    @Secured("ROLE_USER")
    public void deleteQuestion(String id, String uId) {
        Long lId = Long.parseLong(id);
        Long lUId = Long.parseLong(uId);
        Question toDelete = questionRepository.findOne(lId);
        if (toDelete.getUser().getId() != lUId) {
            //TODO evtl. mit Spring Security schöner lösen
            throw new IllegalArgumentException("The UserIds don´t match");
        }
        Collection<Answer> answersToQuestion = new LinkedList<>();
        questionRepository.delete(lId);
        //TODO Prüfen ob diese nötig ist und evtl. wieder entfernen bzw. parallel zum Löschen der Frage.
        answersToQuestion.addAll(toDelete.getAnswers());
        answerService.deleteAnswerList(answersToQuestion);
    }

    //=======NON-API=======

    /**
     * Konvertiert eine Liste an Fragen in eine Liste an Fragen DTOs, wobei nur ID, Titel, Text und User angegeben werden.
     *
     * @param questions Liste der zu konvertierenden Fragen
     * @return Liste an QuestionDTOs.
     */
    List<QuestionDTO> convertListToDTOs(Collection<Question> questions) {
        if (questions == null) {
            return new LinkedList<>();
        }
        List<QuestionDTO> questionDTOs = new LinkedList<>();
        for (Question q : questions) {
            QuestionDTO newDTO = new QuestionDTO();
            newDTO.setId(q.getId());
            newDTO.setTitle(q.getTitle());
            newDTO.setText(q.getText());
            newDTO.setDate(q.getDate().toString());
            newDTO.setUser(q.getUser().getId());
            newDTO.setAnswerCount(q.getAnswers().size());
            newDTO.setAnswered(q.isAnswered());
            questionDTOs.add(newDTO);
        }
        return questionDTOs;
    }

    /**
     * Konvertiert ein QuestionDTO in eine Frage.
     *
     * @param question Das DTO, das zu einer Frage konvertiert werden soll
     * @return Die Frage, die die Infos aus dem DTO enthält.
     */
    private Question convertDTOToQuestion(QuestionDTO question) {
        User user = userService.getUserById(question.getUser());
        return new Question(question.getTitle(), question.getText(), user);
    }

    /**
     * Konvertiert eine Frage zu einem QuestionDTO
     *
     * @param saved Die Frage die in ein DTO umgewandelt werden soll
     * @return QuestionDTO, das alle für das Frontend wichtigen Informationen enthält
     */
    private QuestionDTO convertQuestionToDTO(Question saved) {
        if (saved == null) {
            return new QuestionDTO();
        }
        QuestionDTO question = new QuestionDTO();
        question.setId(saved.getId());
        question.setTitle(saved.getTitle());
        question.setText(saved.getText());
        question.setUser(saved.getUser().getId());
        question.setAnswered(saved.isAnswered());
        question.setDate(saved.getDate().toString());
        question.setAnswerCount(saved.getAnswers().size());
        return question;
    }

    /**
     * Setzt die Frage mit der entsprechenden {@code id} auf {@code answered}
     *
     * @param id       Die eindeutige Id der Frage
     * @param answered True, falls die Frage als beantwortet gilt
     *                 False, falls die Frage noch zu benatworten ist.
     */
    void setQuestionToAnswered(String id, boolean answered) {
        Long lQuestionId = Long.parseLong(id);
        questionRepository.updateAnswered(lQuestionId, answered);
    }

    /**
     * Gibt die Frage mit der entsprecheden {@code id} zurück
     *
     * @param id Die eindeutige Id der Frage
     * @return Die Frage mit der entsprechenden Id
     */
    Question getQuestionById(String id) {
        Long lQuestionId = Long.parseLong(id);
        return questionRepository.findOne(lQuestionId);
    }

    /**
     * Gibt alle Fragen, die ein bestimmter Nutzer gestellt hat zurück.
     *
     * @param id Die eindeutige Id des Benutzers
     * @return Alle Fragen, die der Benutzer gestellt hat
     */
    Collection<Question> findAllByUserId(String id) {
        Long lUserId = Long.parseLong(id);
        return questionRepository.findAllByUserId(lUserId);
    }

    /**
     * Gibt alle Fragen, auf die ein übergebener Benutzer geantwortet hat, als DTO-Liste zurück.
     *
     * @param id Eindeutiger Identifikator des Benutzers
     * @return Liste aller Fragen, auf die der Benutzer mit {@code id} geantwortet hat.
     */
    List<QuestionDTO> findAllQuestionsByAnswerContainsUserId(String id) {
        Long lUserId = Long.parseLong(id);
        return convertListToDTOs(questionRepository.findAllByAnswersContainsUserId(lUserId));
    }
}
