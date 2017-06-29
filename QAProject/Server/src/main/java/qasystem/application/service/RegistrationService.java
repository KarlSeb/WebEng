package qasystem.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qasystem.application.exceptions.UserAlreadyExistsException;
import qasystem.persistence.entities.User;
import qasystem.persistence.repositories.UserRepository;
import qasystem.web.dtos.UserDTO;

@Service
public class RegistrationService {
    @Autowired
    private UserRepository userRepository;

    public void registerNewUser(UserDTO accountDto) {
        if (userExists(accountDto.getUserName())){
            throw new UserAlreadyExistsException(accountDto.getUserName());
        }
        User user = new User(accountDto.getUserName(), accountDto.getPassword());
        userRepository.save(user);
    }

    private boolean userExists(String userName) {
        return userRepository.findByUsername(userName) != null;
    }
}
