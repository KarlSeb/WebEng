package qasystem.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import qasystem.persistence.entities.User;

/**
 * Repository für die User die im System registriert sind
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
