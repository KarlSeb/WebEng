package qasystem.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import qasystem.application.service.RegistrationService;
import qasystem.web.dtos.UserDTO;
import javax.validation.Valid;

/**
 * Stellt die generelle Schnittstelle mit HTTP-Request dar, die Anfragen wie die Registrierung eines Benutzers
 * entgegen nimmt.
 */
@RestController
public class GenericController {

    private final RegistrationService registrationService;

    @Autowired
    public GenericController(RegistrationService registrationService){
        this.registrationService = registrationService;
    }

    /**
     * Stellt die Funktionalität für das Registrieren eines neuen Nutzers für die Rest-API zur Verfügung.
     * Leitet die Anfrage an den registrationService{@link RegistrationService} weiter.
     *
     * @param accountDto DTO, das alle Informationen des neu angelegten Benutzers beinhaltet.
     */
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void registerNewUser(@Valid UserDTO accountDto){
        registrationService.registerNewUser(accountDto);
    }
}
