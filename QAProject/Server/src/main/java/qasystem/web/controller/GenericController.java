package qasystem.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import qasystem.application.service.RegistrationService;
import qasystem.web.dtos.UserDTO;
import javax.validation.Valid;

@RestController
public class GenericController {

    private final RegistrationService registrationService;

    @Autowired
    public GenericController(RegistrationService registrationService){
        this.registrationService = registrationService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void registerNewUser(@Valid UserDTO accountDto){
        registrationService.registerNewUser(accountDto);
    }
}
