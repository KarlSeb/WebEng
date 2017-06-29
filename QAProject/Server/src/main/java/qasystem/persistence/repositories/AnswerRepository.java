package qasystem.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import qasystem.persistence.entities.Answer;

/**
 * Repository für die Antworten auf eine Frage
 */
public interface AnswerRepository extends JpaRepository<Answer, Long>{

}
