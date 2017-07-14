package qasystem.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import qasystem.persistence.entities.Answer;

import java.util.Collection;

/**
 * Repository für die Antworten auf eine Frage
 */
public interface AnswerRepository extends JpaRepository<Answer, Long>{

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update Answer a set a.accepted = :accepted where a.id = :answerId")
    void updateAccepted(@Param("answerId") long answerId, @Param("accepted") boolean accepted);

    @Query(value = "select a from Answer a where a.parentQuestion.id = :questionId")
    Collection<Answer> findAllByParentQuestion(@Param("questionId") long questionId);

    @Query(value = "select a from Answer a where a.user.id = :userId")
    Collection<Answer> findAllByUser(@Param("userId") long userId);
}
