package qasystem.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import qasystem.persistence.entities.Role;

/**
 * Repository für die Fragen, die gestellt worden sind
 */
public interface RoleRepository extends JpaRepository<Role, String> {
}
