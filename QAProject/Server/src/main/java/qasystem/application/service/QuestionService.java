package qasystem.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import qasystem.persistence.entities.Answer;
import qasystem.persistence.entities.Question;
import qasystem.persistence.repositories.QuestionRepository;
import qasystem.web.dtos.QuestionDTO;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.GregorianCalendar;
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
    public QuestionDTO createQuestion(QuestionDTO newQuestion, User user) {
        if (newQuestion == null) {
            throw new IllegalArgumentException("Given Question was null");
        }
        qasystem.persistence.entities.User foundUser = userService.getUserByAuthenticationPrinciple(user);
        newQuestion.setUser(foundUser.getId());
        final Question question = convertDTOToQuestion(newQuestion);
        return convertQuestionToDTO(questionRepository.save(question));
    }

    /**
     * Löscht die Frage, die durch {@code id} identifiziert wird, und alle darunter liegenden Antworten, falls der
     * User, der mittels {@code uId} identifiziert wird, der Ersteller der Frage ist.
     *
     * @param id   Eindeutiger Identifikator für die Frage
     * @param user Benutzer, der die Frage löschen möchte
     */
    @Secured("ROLE_USER")
    public void deleteQuestion(String id, User user) {
        authenticateUserForQuestion(id, user);
        Long lId = Long.parseLong(id);
        Question toDelete = questionRepository.findOne(lId);
        Collection<Answer> answersToQuestion = new LinkedList<>();
        answersToQuestion.addAll(toDelete.getAnswers());
        answerService.deleteAnswerList(answersToQuestion);
        questionRepository.delete(lId);
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
            newDTO.setDate(format(q.getDate()));
            newDTO.setUser(q.getUser().getId());
            newDTO.setAnswerCount(q.getAnswers().size());
            newDTO.setAnswered(q.isAnswered());
            newDTO.setUserName(q.getUser().getUsername());
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
        qasystem.persistence.entities.User user = userService.getUserById(question.getUser());
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
        question.setDate(format(saved.getDate()));
        question.setAnswerCount(saved.getAnswers().size());
        question.setUserName(saved.getUser().getUsername());
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
        if (id == null) {
            throw new IllegalArgumentException("Given QuestionId was null!");
        }
        Long lQuestionId = Long.parseLong(id);
        if (answered) {
            questionRepository.updateAnswered(lQuestionId, answered);
        } else {
            boolean stayAnswered = false;
            int count = 0;
            Question q = questionRepository.getOne(lQuestionId);
            for (Answer a : q.getAnswers()) {
                if (a.isAccepted()) {
                    count++;
                }
                if (count == 2) {
                    stayAnswered = true;
                    break;
                }
            }
            questionRepository.updateAnswered(lQuestionId, stayAnswered);
        }
    }

    /**
     * Gibt die Frage mit der entsprecheden {@code id} zurück
     *
     * @param id Die eindeutige Id der Frage
     * @return Die Frage mit der entsprechenden Id
     */
    Question getQuestionById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Given QuestionId was null");
        }
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
        if (id == null) {
            throw new IllegalArgumentException("Given UserId was null");
        }
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
        if (id == null) {
            throw new IllegalArgumentException("Given UserId was null");
        }
        Long lUserId = Long.parseLong(id);
        return convertListToDTOs(questionRepository.findAllByAnswersContainsUserId(lUserId));
    }

    void authenticateUserForQuestion(String questionId, User user) {
        qasystem.persistence.entities.User foundUser = userService.getUserByAuthenticationPrinciple(user);
        Long lQuestionId = Long.parseLong(questionId);
        boolean allowed = false;
        for (Question q : foundUser.getQuestions()) {
            if (q.getId() == lQuestionId) {
                allowed = true;
                break;
            }
        }
        if (!allowed) throw new SecurityException("This User is not allowed to take that action.");
    }

    private static String format(GregorianCalendar calendar) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        fmt.setCalendar(calendar);
        String dateFormatted = fmt.format(calendar.getTime());
        return dateFormatted;
    }
}
