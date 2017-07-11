package qasystem.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception die geworfen wird, falls der Nutzername bereits von einem anderen Benutzer verwendet wurde.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String username) {
        super("The username "+username+" is already taken!");
    }
}
