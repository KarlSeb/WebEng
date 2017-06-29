package qasystem.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import qasystem.persistence.entities.User;

/**
 * Repository für die User die im System registriert sind
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
