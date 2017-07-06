package qasystem.application.service;

import org.springframework.beans.factory.annotation.Autowired;
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
        if(all == null){
            return new LinkedList<>();
        }
        return convertListToDTOs(all);
    }

    /**
     * Liefert eine Liste aller Fragen, die noch als unbeantwortet gelten.
     *
     * @return Liste aller Fragen, die keine akzeptierte Antwort besitzen
     */
    public List<QuestionDTO> getAllUnansweredQuestions() {
        return convertListToDTOs(questionRepository.findAllByAnsweredNot());
    }

    /**
     * Liefert eine Liste aller Fragen, die noch keine Antwort haben.
     *
      * @return Liste aller Fragen, denen noch keine Antwort zugeordnet ist.
     */
    public List<QuestionDTO> getAllUnsolvedQuestions() {
        return convertListToDTOs(questionRepository.findAllByNoReply());
    }

    /**
     * Erstellt eine neue Frage im System.
     *
     * @return Die Frage als DTO
     */
    public QuestionDTO createQuestion(QuestionDTO newQuestion) {
        final Question question = convertDTOToQuestion(newQuestion);
        return convertQuestionToDTO(questionRepository.save(question));
    }

    /**
     * Löscht die Frage, die durch {@code id} identifiziert wird, und alle darunter liegenden Antworten, falls der
     * User, der mittels {@code uId} identifiziert wird, der Ersteller der Frage ist.
     *
     * @param id Eindeutiger Identifikator für die Frage
     * @param uId Eindeutiger Identifikator für die Frage
     */
    public void deleteQuestion(String id, String uId) {
        Long lId = Long.getLong(id);
        Long lUId = Long.getLong(uId);
        Question toDelete = questionRepository.findOne(lId);
        if(toDelete.getUser().getId() != lUId){
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
        List<QuestionDTO> questionDTOs = new LinkedList<>();
        for(Question q: questions){
            QuestionDTO newDTO = new QuestionDTO();
            newDTO.setId(q.getId());
            newDTO.setTitle(q.getTitle());
            newDTO.setText(q.getText());
            newDTO.setUser(q.getUser().getId());
            questionDTOs.add(newDTO);
        }
        return questionDTOs;
    }

    private Question convertDTOToQuestion(QuestionDTO question) {
        User user = userService.getUserById(question.getUser());
        return new Question(question.getTitle(), question.getText(), user);
    }

    private QuestionDTO convertQuestionToDTO(Question saved) {
        QuestionDTO question = new QuestionDTO();
        question.setId(saved.getId());
        question.setTitle(saved.getTitle());
        question.setText(saved.getText());
        question.setUser(saved.getUser().getId());
        question.setAnswered(saved.isAnswered());
        question.setDate(saved.getDate());
        Collection<Long> answerIds = new LinkedList<>();
        for(Answer a: saved.getAnswers()){
            answerIds.add(a.getId());
        }
        question.setAnswers(answerIds);
        return question;
    }

    void setQuestionToAnswered(String id, boolean answered) {
        Long lQuestionId = Long.getLong(id);
        questionRepository.updateAnswered(lQuestionId, answered);
    }

    Question getQuestionById(String id) {
        Long lQuestionId = Long.getLong(id);
        return questionRepository.findOne(lQuestionId);
    }

    Collection<Question> findAllByUserId(String id) {
         Long lUserId = Long.getLong(id);
         return questionRepository.findAllByUserId(lUserId);
    }
}
