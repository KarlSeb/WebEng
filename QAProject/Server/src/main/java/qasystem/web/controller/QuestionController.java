package qasystem.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import qasystem.application.service.QuestionService;
import qasystem.web.dtos.QuestionDTO;

import java.util.List;

@RestController
@RequestMapping(value = "/questions")
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService){
        this.questionService = questionService;
    }

    //=======GET-MAPPING=======
    @GetMapping(value = "/all")
    public List<QuestionDTO> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping(value = "/unanswered")
    public List<QuestionDTO> getAllUnansweredQuestions(){
        return null;
    }

    @GetMapping(value = "/unsolved")
    public List<QuestionDTO> getAllUnsolvedQuestions(){
        return null;
    }

    @GetMapping(value = "/{id:[1-9]+}/answers")
    public QuestionDTO getAllAnswersByQuestionId(@PathVariable("id") String id){
        return null;
    }

    //=======POST-MAPPING=======
    @PostMapping(value = "/{id:[1-9]+}/answers/newAnswer")
    @ResponseStatus(HttpStatus.CREATED)
    public void answerQuestion(@PathVariable("id") String id){

    }

    //=======PUT-MAPPING=======
    @PutMapping(value = "/{id:[1-9]+}/answers/{aId:[1-9]+}")
    @ResponseStatus(HttpStatus.OK)
    public void acceptAnswer(@PathVariable("id") String id, @PathVariable("aId") String aId){

    }

    //=======DELETE-MAPPING=======
    @DeleteMapping (value = "/{id:[1-9]+}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteQuestion(@PathVariable("id") String id){}

    @DeleteMapping (value = "/{id:[1-9]+}/answers/{aId:[1-9]+}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAnswer(@PathVariable("id") String id, @PathVariable("aId") String aId){}
}
