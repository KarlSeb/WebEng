package qasystem.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import qasystem.application.exceptions.UserAlreadyExistsException;
import qasystem.persistence.entities.User;
import qasystem.persistence.repositories.UserRepository;
import qasystem.web.dtos.UserDTO;

/**
 * Stellt die Funktionalität für das Registrieren eines neuen Nutzers bereit.
 */
@Service
public class RegistrationService extends MyUserDetailsService{

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        super(userRepository);
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Prüft ob der Account-Name bereits vergeben ist:
     * {@code true} Eine UserAlreadyExistsException{@Link UserAlreadyExistsException} wird geworfen
     * {@code false} Ein neuer User wird angelegt und im UserRepository gespeichert
     *
     * @param accountDto DTO das die Daten des neuen Benutzers enthält
     */
    public UserDTO registerNewUser(UserDTO accountDto) {
        if (userExists(accountDto.getUserName())){
            throw new UserAlreadyExistsException(accountDto.getUserName());
        }
        User user = new User(accountDto.getUserName(), passwordEncoder.encode(accountDto.getPassword()));
        return convertUserToDTO(userRepository.save(user));
    }

    private UserDTO convertUserToDTO(User save) {
        UserDTO user = new UserDTO();
        user.setId(save.getId());
        user.setUserName(save.getUsername());
        return user;
    }

    /**
     * Prüft ob der übergebene Nutzername bereits in der Datenbank gespeichert wurde.
     *
     * @param userName Der gesuchte Nutzername
     * @return {@code true}, falls der Nutzername in der Datebank vorhanden ist.
     *          {@code false}, falls der Nutzername nicht in der Datenbank vorhanden ist.
     */
    private boolean userExists(String userName) {
        return userRepository.findByUsername(userName) != null;
    }
}
