package qasystem.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import qasystem.persistence.entities.Answer;
import qasystem.persistence.entities.Question;

import java.util.Collection;

/**
 * Repository für die Antworten auf eine Frage
 */
public interface AnswerRepository extends JpaRepository<Answer, Long>{

    @Query(value = "update Answer a set a.accepted = :accepted where a.id = :answerId")
    Collection<Question> updateAccepted(@Param("answerId") long answerId, @Param("accepted") boolean accepted);
}
