package qasystem.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import qasystem.persistence.entities.Question;

/**
 * Repository für die Fragen, die gestellt worden sind
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
