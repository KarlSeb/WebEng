package qasystem.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qasystem.application.service.UserService;
import qasystem.web.dtos.AnswerDTO;
import qasystem.web.dtos.QuestionDTO;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(value = "/{id:[1-9]+}/questions")
    public List<QuestionDTO> getAllQuestionsOfUser(@PathVariable("id") String id){
        return userService.getAllQuestionsOfUser(id);
    }

    @GetMapping(value = "/{id:[1-9]+}/answers")
    public List<AnswerDTO> getAllAnswersOfUser(@PathVariable("id") String id){
        return userService.getAllAnswersOfUser(id);
    }
}
