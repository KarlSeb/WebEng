package qasystem.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qasystem.persistence.entities.Answer;
import qasystem.persistence.repositories.AnswerRepository;
import qasystem.web.dtos.AnswerDTO;

import java.util.Collection;
import java.util.List;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    //TODO
    public List<AnswerDTO> getAllAnswersByQuestionId(String id) {
        return null;
    }

    //TODO
    public AnswerDTO answerQuestion(String id) {
        return null;
    }

    //TODO
    public void acceptAnswer(String id, String aId) {
    }

    //TODO
    public void deleteAnswer(String id, String aId, String uId) {
    }

    //TODO
    public void deleteAnswerList(Collection<Answer> answersToQuestion) {
    }
}
