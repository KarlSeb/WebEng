package qasystem.application.exceptions;


public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String username) {
        super("The username "+username+" is already taken!");
    }
}
