package qasystem.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import qasystem.persistence.entities.Role;
import qasystem.persistence.entities.User;

/**
 * Repository für die Fragen, die gestellt worden sind
 */
public interface RoleRepository extends JpaRepository<Role, String> {
}
