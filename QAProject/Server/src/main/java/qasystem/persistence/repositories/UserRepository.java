package qasystem.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import qasystem.persistence.entities.User;

/**
 * Repository für die User die im System registriert sind
 */
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select u from User u where u.username like :username")
    User findByUsername(@Param("username") String username);
}
