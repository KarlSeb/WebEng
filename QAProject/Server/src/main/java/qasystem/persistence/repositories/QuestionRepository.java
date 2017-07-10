package qasystem.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import qasystem.persistence.entities.Question;
import qasystem.persistence.entities.User;

import java.util.Collection;

/**
 * Repository für die Fragen, die gestellt worden sind
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "select q from Question q where q.answered = false")
    Collection<Question> findAllByAnsweredNot();

    @Query(value = "select q from Question q where q.user.id = :UserId")
    Collection<Question> findAllByUserId(@Param("UserId") long UserId);

    @Query(value = "select q from Question q where q.id not in (select distinct q2.id from Question q2, Answer a where a.parentQuestion = q2)")
    Collection<Question> findAllByNoReply();

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update Question q set q.answered = :answered where q.id = :questionId")
    void updateAnswered(@Param("questionId") long questionId, @Param("answered") boolean answered);

    @Query(value = "select q from Question q, Answer a where a.user.id = :id and a.parentQuestion.id = q.id")
    Collection<Question> findAllByAnswersContainsUserId(@Param("id") long id);


}
