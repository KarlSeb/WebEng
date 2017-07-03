package qasystem.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qasystem.persistence.entities.Question;
import qasystem.persistence.repositories.QuestionRepository;
import qasystem.web.dtos.QuestionDTO;
import qasystem.web.dtos.UserDTO;

import java.util.LinkedList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<QuestionDTO> getAllQuestions() {
        List<Question> all = questionRepository.findAll();
        return convertListToDTOs(all);
    }

    private List<QuestionDTO> convertListToDTOs(List<Question> all) {
        List<QuestionDTO> allDTOs = new LinkedList<>();
        for(Question q: all){
            QuestionDTO newDTO = new QuestionDTO();
            UserDTO userDTO = new UserDTO();
            userDTO.setUserName(q.getUser().getUsername());
            userDTO.setId(q.getUser().getId());
            newDTO.setId(q.getId());
            newDTO.setTitle(q.getTitle());
            newDTO.setText(q.getText());
            newDTO.setUser(userDTO);
            allDTOs.add(newDTO);
        }
        return allDTOs;
    }
}
